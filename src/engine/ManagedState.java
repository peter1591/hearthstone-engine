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
 * Provide interface for client card to modify the game state.
 * 
 * We need this class to trigger events when certain states is changed.
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
	
	public ReadableProperty getEntityProperty(int entityId) {
		return state.getEntityManager().get(entityId).getFinalProperty();
	}
	
	private EventArgument invokeBeforeZoneChangeEvents(int entityId, PlayerId toSide, Zone toZone) {
		Entity entity = state.getEntityManager().get(entityId);
		Entity boardEntity = state.getEntityManager().get(getBoardEntityId());
		EventArgument argument = new EventArgument(entity, getBoardEntityId());
		argument.side = toSide;
		argument.zone = toZone;
		entity.getEventManager().invoke(Event.BEFORE_ZONE_CHANGE, this, argument);
		boardEntity.getEventManager().invoke(Event.BEFORE_ZONE_CHANGE, this, argument);
		return argument;
	}
	
	private void invokeRemovedFromZoneEvents(int entityId, PlayerId fromSide, Zone fromZone) {
		Entity entity = state.getEntityManager().get(entityId);
		Entity boardEntity = state.getEntityManager().get(getBoardEntityId());
		EventArgument argument = new EventArgument(entity, getBoardEntityId());
		argument.side = fromSide;
		argument.zone = fromZone;
		entity.getEventManager().invoke(Event.REMOVED_FROM_ZONE, this, argument);
		boardEntity.getEventManager().invoke(Event.REMOVED_FROM_ZONE, this, argument);
	}
	
	private void invokeAddedToZoneEvents(int entityId, PlayerId fromSide, Zone fromZone) {
		Entity entity = state.getEntityManager().get(entityId);
		Entity boardEntity = state.getEntityManager().get(getBoardEntityId());
		EventArgument argument = new EventArgument(entity, getBoardEntityId());
		argument.side = fromSide;
		argument.zone = fromZone;
		entity.getEventManager().invoke(Event.ADDED_TO_ZONE, this, argument);
		boardEntity.getEventManager().invoke(Event.ADDED_TO_ZONE, this, argument);
	}
	
	public void changeZone(int entityId, PlayerId side, Zone zone) {
		EventArgument argument = invokeBeforeZoneChangeEvents(getBoardEntityId(), side, zone);
		if (argument.abort) return;
		
		side = argument.side;
		zone = argument.zone;
		
		Entity entity = state.getEntityManager().get(entityId);
		PlayerId fromSide = entity.getFinalProperty().getSide();
		entity.getMutableProperty().setSide(side);
		Zone fromZone = entity.getFinalProperty().getZone();
		entity.getMutableProperty().setZone(zone);
		
		invokeRemovedFromZoneEvents(entityId, fromSide, fromZone);
		invokeAddedToZoneEvents(entityId, fromSide, fromZone);
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
		
		EventArgument argument = new EventArgument(entity, triggererId);
		argument.amount = originHp - newHp;
		entity.getEventManager().invoke(Event.ON_DAMAGED, this, argument);
	}
}
