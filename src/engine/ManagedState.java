package engine;

import engine.board.ReadablePlayer;

import java.util.function.Function;

import engine.board.Board.PlayerId;
import engine.entity.Entity;
import engine.entity.ReadableProperty;
import engine.entity.ReadableProperty.Zone;
import engine.event.EntityEventArgument;
import engine.event.EntityEventManager;
import engine.event.EventAddedToZone;
import engine.event.EventAfterTurnStarted;
import engine.event.EventArgument;
import engine.event.EventBeforeTurnEnd;
import engine.event.EventBeforeZoneChange;
import engine.event.EventCheckPlayable;
import engine.event.EventHandlers;
import engine.event.EventOnDamaged;
import engine.event.EventOnPlay;
import engine.event.EventRemovedFromZone;
import engine.event.FlowEventManager;
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
	
	public EntityEventManager getEntityEventManager(int entityId) {
		return state.getEntityManager().get(entityId).getEventManager();
	}
	
	private boolean checkCardPlayable(int entityId) {
		int cost = getEntityProperty(entityId).getCost();
		int resource = getEntityProperty(getCurrentPlayer().getPlayerEntityId()).getResourceCurrent();
	
		if (resource < cost)
			return false;
		
		return invokeCheckPlayableEvent(entityId).playable;
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
	
	private <T extends EntityEventArgument>
	T invokeEvent(int entityId, T argument, Function<EntityEventManager, EventHandlers<T>> handlersGetter) {
		Entity entity = state.getEntityManager().get(entityId);
		argument.owner = entity;

		if (state.getFlowContext().eventInvokeDepth <= FlowContext.MAX_EVENT_INVOKE_DEPTH) {
			state.getFlowContext().eventInvokeDepth++;
			handlersGetter.apply(entity.getEventManager()).invoke(this, argument);
			state.getFlowContext().eventInvokeDepth++;
		}
		
		return argument;
	}
	
	private <T extends EventArgument> T invokeFlowEvent(T argument, Function<FlowEventManager, EventHandlers<T>> handlersGetter) {
		if (state.getFlowContext().eventInvokeDepth <= FlowContext.MAX_EVENT_INVOKE_DEPTH) {
			state.getFlowContext().eventInvokeDepth++;
			handlersGetter.apply(state.getFlowEventManager()).invoke(this, argument);
			state.getFlowContext().eventInvokeDepth++;
		}
		
		return argument;
	}

	public EventCheckPlayable.Argument invokeCheckPlayableEvent(int entityId) {
		EventCheckPlayable.Argument argument = new EventCheckPlayable.Argument();
		invokeEvent(entityId, argument, (mgr) -> mgr.checkPlayable());
		return argument;
	}
	
	public EventArgument invokeOnPlayEvent(int entityId) {
		EventOnPlay.Argument argument = new EventOnPlay.Argument();
		invokeEvent(entityId, argument, (mgr) -> mgr.onPlay());
		return argument;
	}
	
	private EventBeforeZoneChange.Argument invokeBeforeZoneChangeEvents(int entityId, PlayerId toSide, Zone toZone) {
		EventBeforeZoneChange.Argument argument = new EventBeforeZoneChange.Argument(toSide, toZone);
		invokeFlowEvent(argument, (mgr) -> mgr.beforeZoneChange());
		return argument;
	}
	
	private void invokeRemovedFromZoneEvents(int entityId, PlayerId fromSide, Zone fromZone) {
		EventRemovedFromZone.Argument argument = new EventRemovedFromZone.Argument(fromSide, fromZone);
		invokeFlowEvent(argument, (mgr) -> mgr.removedFromZone());
		invokeEvent(entityId, argument, (mgr) -> mgr.removedFromZone());
	}
	
	private void invokeAddedToZoneEvents(int entityId, PlayerId fromSide, Zone fromZone) {
		EventAddedToZone.Argument argument = new EventAddedToZone.Argument(fromSide, fromZone);
		invokeFlowEvent(argument, (mgr) -> mgr.addedToZone());
		invokeEvent(entityId, argument, (mgr) -> mgr.addedToZone());
	}
	
	public void invokeBeforeTurnEndEvents() {
		EventBeforeTurnEnd.Argument argument = new EventBeforeTurnEnd.Argument();
		invokeFlowEvent(argument, (mgr) -> mgr.beforeTurnEnd());
	}
	public void invokeAfterTurnStartedEvents() {
		EventAfterTurnStarted.Argument argument = new EventAfterTurnStarted.Argument();
		invokeFlowEvent(argument, (mgr) -> mgr.afterTurnStarted());
	}
	
	public void changeZone(int entityId, PlayerId side, Zone zone) {
		EventBeforeZoneChange.Argument argument = invokeBeforeZoneChangeEvents(getBoardEntityId(), side, zone);
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
	
	public void damage(int triggererId, int entityId, int val) {
		Entity entity = state.getEntityManager().get(entityId);
		
		int originHp = entity.getFinalProperty().getHp();
		int newHp = originHp - val;
		if (newHp < 0) newHp = 0;
		entity.getMutableProperty().setHp(newHp);
		
		EventOnDamaged.Argument argument = new EventOnDamaged.Argument(originHp-newHp);
		invokeEvent(entityId, argument, (mgr) -> mgr.onDamaged());
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
