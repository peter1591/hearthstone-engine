package engine;

import engine.board.ReadablePlayer;
import engine.board.Board.PlayerId;
import engine.entity.Entity;
import engine.entity.ReadableProperty;
import engine.event.Event;
import engine.event.EventArgument;
import engine.event.EventHandler;

/**
 * Provide interface for client card to modify the game state.
 * 
 * We need this class to trigger events when certain states is changed.
 * 
 * @author petershih
 *
 */
public class ManagedState {
	State state;
	
	public ManagedState(State state) {
		this.state = state;
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
