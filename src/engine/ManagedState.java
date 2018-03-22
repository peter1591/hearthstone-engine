package engine;

import engine.board.ReadablePlayer;
import engine.board.Board.PlayerId;
import engine.entity.Entity;
import engine.entity.ReadableProperty;
import engine.entity.ReadableProperty.Zone;
import engine.event.Event;
import engine.event.EventArgument;
import engine.event.EventHandler;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;

/**
 * Coordinate the changes in board, entities, and event triggers.
 * 
 * @author petershih
 *
 */
public class ManagedState implements DeepCopyable<ManagedState>, CopyableAsBase<ManagedState> {
	State state;
	
	private ManagedState() {
		
	}
	
	public static ManagedState create(State state) {
		ManagedState ret = new ManagedState();
		ret.state = state;
		return ret;
	}

	@Override
	public ManagedState copyAsBase() {
		ManagedState ret = new ManagedState();
		ret.state = state.copyAsBase();
		return ret;
	}

	@Override
	public ManagedState deepCopy() {
		ManagedState ret = new ManagedState();
		ret.state = state.deepCopy();
		return ret;
	}
	
	public FlowContext getFlowContext() {
		return state.getFlowContext();
	}
	
	public int getBoardEntityId() {
		return state.getBoard().getBoardEntityId();
	}
	
	public ReadablePlayer getPlayer(PlayerId playerId) {
		return state.getBoard().get(playerId);
	}
	
	public PlayerId getCurrentPlayerId() {
		return state.getBoard().getCurrentPlayerId();
	}
	public ReadablePlayer getCurrentPlayer() {
		return state.getBoard().getCurrentPlayer();
	}
	
	public ReadableProperty getEntityProperty(int entityId) {
		return state.getEntityManager().get(entityId).getFinalProperty();
	}
	
	private boolean checkCardPlayable(int entityId) {
		int cost = getEntityProperty(entityId).getCost();
		int resource = getEntityProperty(getCurrentPlayer().getPlayerEntityId()).getResourceCurrent();
	
		if (resource < cost)
			return false;
		
		return invokeCheckPlayableEvent(entityId).booleanResult;
	}
	
	private boolean checkAttackable(int entityId) {
		return getEntityProperty(entityId).getAttack() > 0;
	}
	
	private boolean checkHeroPowerUsable(int entityId) {
		return checkCardPlayable(entityId);
	}
	
	private void checkPlayable() {
		int cards = getCurrentPlayer().getHandCount();
		for (int i=0; i<cards; ++i) {
			int entityId = getCurrentPlayer().getHandEntityId(i);
			boolean playable = checkCardPlayable(entityId);
			state.getEntityManager().get(entityId).getMutableProperty().setPlayable(playable);
		}
		
		int heroEntity = getCurrentPlayer().getHeroEntityId();
		state.getEntityManager().get(heroEntity).getMutableProperty().setPlayable(checkCardPlayable(heroEntity));
		
		int minions = getCurrentPlayer().getMinionsCount();
		for (int i=0; i<minions; ++i) {
			int entityId = getCurrentPlayer().getMinionEntityId(i);
			state.getEntityManager().get(entityId).getMutableProperty().setPlayable(checkAttackable(entityId));
		}
		
		int heroPowerEntity = getCurrentPlayer().getHeroPowerEntityId();
		state.getEntityManager().get(heroPowerEntity).getMutableProperty().setPlayable(checkHeroPowerUsable(heroPowerEntity));
	}
	
	public void finalCleanUp() {
		checkPlayable();
	}
	
	private EventArgument invokeEvent(Event event, int entityId, int triggerer, EventArgument argument) {
		Entity entity = state.getEntityManager().get(entityId);
		argument.owner = entity;
		argument.triggerer = triggerer;
		entity.getEventManager().invoke(event, this, argument);
		return argument;
	}

	public EventArgument invokeCheckPlayableEvent(int entityId) {
		EventArgument argument = new EventArgument();
		argument.booleanResult = true;
		invokeEvent(Event.CHECK_PLAYABLE, entityId, getBoardEntityId(), argument);
		return argument;
	}
	
	public EventArgument invokeOnPlayEvent(int entityId) {
		EventArgument argument = new EventArgument();
		invokeEvent(Event.ON_PLAY, entityId, getBoardEntityId(), argument);
		return argument;
	}
	
	private EventArgument invokeBeforeZoneChangeEvents(int entityId, PlayerId toSide, Zone toZone) {
		EventArgument argument = new EventArgument();
		argument.side = toSide;
		argument.zone = toZone;
		invokeEvent(Event.BEFORE_ZONE_CHANGE, getBoardEntityId(), getBoardEntityId(), argument);
		invokeEvent(Event.BEFORE_ZONE_CHANGE, entityId, getBoardEntityId(), argument);
		return argument;
	}
	
	private void invokeRemovedFromZoneEvents(int entityId, PlayerId fromSide, Zone fromZone) {
		EventArgument argument = new EventArgument();
		argument.side = fromSide;
		argument.zone = fromZone;
		invokeEvent(Event.REMOVED_FROM_ZONE, getBoardEntityId(), getBoardEntityId(), argument);
		invokeEvent(Event.REMOVED_FROM_ZONE, entityId, getBoardEntityId(), argument);
	}
	
	private void invokeAddedToZoneEvents(int entityId, PlayerId fromSide, Zone fromZone) {
		EventArgument argument = new EventArgument();
		argument.side = fromSide;
		argument.zone = fromZone;
		invokeEvent(Event.ADDED_TO_ZONE, getBoardEntityId(), getBoardEntityId(), argument);
		invokeEvent(Event.ADDED_TO_ZONE, entityId, getBoardEntityId(), argument);
	}
	
	public void invokeBeforeTurnEndEvents() {
		EventArgument argument = new EventArgument();
		invokeEvent(Event.BEFORE_TURN_END, getBoardEntityId(), getBoardEntityId(), argument);
	}
	public void invokeAfterTurnStartedEvents() {
		EventArgument argument = new EventArgument();
		invokeEvent(Event.AFTER_TURN_STARTED, getBoardEntityId(), getBoardEntityId(), argument);
	}
	
	public void changeZone(int entityId, PlayerId side, Zone zone) {
		EventArgument argument = invokeBeforeZoneChangeEvents(getBoardEntityId(), side, zone);
		if (argument.abort) return;
		
		side = argument.side;
		zone = argument.zone;
		
		Entity entity = state.getEntityManager().get(entityId);
		PlayerId fromSide = entity.getFinalProperty().getSide();
		Zone fromZone = entity.getFinalProperty().getZone();
		
		entity.getMutableProperty().setSide(side);
		entity.getMutableProperty().setZone(zone);
		
		invokeRemovedFromZoneEvents(entityId, fromSide, fromZone);
		invokeAddedToZoneEvents(entityId, fromSide, fromZone);
	}
	
	public void changeZone(int entityId, Zone zone) {
		Entity entity = state.getEntityManager().get(entityId);
		PlayerId side = entity.getFinalProperty().getSide();
		changeZone(entityId, side, zone);
	}
	
	public int addEvent(int entityId, Event event, EventHandler handler) {
		return state.getEntityManager().get(entityId).getEventManager().add(event, handler);
	}
	
	public void removeEvent(int entityId, Event event, int index, boolean fromOwner) {
		state.getEntityManager().get(entityId).getEventManager().markRemoved(event, index, fromOwner);
	}
	
	public void damage(int triggererId, int entityId, int val) {
		Entity entity = state.getEntityManager().get(entityId);
		
		int originHp = entity.getFinalProperty().getHp();
		int newHp = originHp - val;
		if (newHp < 0) newHp = 0;
		entity.getMutableProperty().setHp(newHp);
		
		EventArgument argument = new EventArgument();
		argument.amount = originHp - newHp;
		invokeEvent(Event.ON_DAMAGED, entityId, triggererId, argument);
	}
	
	public void spendCrystal(PlayerId side, int amount) {
		int entityId = state.getBoard().get(side).getPlayerEntityId();
		Entity entity = state.getEntityManager().get(entityId);
		int resourceCurrent = entity.getFinalProperty().getResourceCurrent();
		resourceCurrent -= amount;
		if (resourceCurrent < 0) resourceCurrent = 0;
		entity.getMutableProperty().setResourceCurrent(resourceCurrent);
	}
	
	public void prepareAttackTargets() {
		// TODO: mark defendable in flow context
	}
	
	public void addMinionToBoard(PlayerId side, int entityId, int putIndex) {
		if (getPlayer(side).isMinionsFull()) return;
		changeZone(entityId, side, Zone.PLAY);
		state.getBoard().get(side).addMinion(entityId, putIndex);
	}
	
	public void markHeroPowerAsUsed(int entityId) {
		state.getEntityManager().get(entityId).getMutableProperty().setPlayable(false);
	}
	
	public void EndTurn() {
		invokeBeforeTurnEndEvents();
		
		state.getBoard().switchCurrentPlayer();
		state.getBoard().increaseTurn();

		invokeAfterTurnStartedEvents();
	}
}
