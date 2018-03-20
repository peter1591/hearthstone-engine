package engine.flow;

import engine.State;
import engine.board.Board.PlayerId;
import engine.board.Player;
import engine.event.Event;
import engine.event.EventArgument;
import engine.event.LambdaEventHandler;
import engine.event.LambdaEventHandler.Operation;

/**
 * Implement the weapon as follows.
 * 
 * 1. Listen to PropertyModifier on hero: add weapon's attack if it's his turn.
 * 
 * 2. Listen to AfterAttacked on hero: weapon loss one durability
 * 
 * @author petershih
 *
 */
public class WeaponFlowHandler {
	static final LambdaEventHandler addWeaponAttack = LambdaEventHandler.create(new Operation() {
		@Override
		public boolean handle(Event event, State state, EventArgument argument) {
			PlayerId side = argument.owner.getFinalProperty().getSide();
			int weaponId = state.getBoard().get(side).getWeaponEntityId();
			int attack = state.getEntityManager().get(weaponId).getFinalProperty().getAttack();
			argument.owner.getMutableProperty().addAttack(attack);
			return true;
		}
	});
	
	static final LambdaEventHandler reduceWeaponDurability = LambdaEventHandler.create(new Operation() {
		@Override
		public boolean handle(Event event, State state, EventArgument argument) {
			PlayerId side = argument.owner.getFinalProperty().getSide();
			int weaponId = state.getBoard().get(side).getWeaponEntityId();
			int attack = state.getEntityManager().get(weaponId).getFinalProperty().getAttack();
			argument.owner.getMutableProperty().addAttack(attack);
			return true;
		}
	});
	
	public static void Process(State state) {
		ProcessPlayer(state, state.getBoard().first());
		ProcessPlayer(state, state.getBoard().second());
	}
	
	static void ProcessPlayer(State state, Player player) {
		state.getEntityManager().get(player.getHeroEntityId()).getEventManager()
			.add(Event.PROPERTY_MODIFIERS, addWeaponAttack);
		state.getEntityManager().get(player.getHeroEntityId()).getEventManager()
			.add(Event.AFTER_ATTACKED, reduceWeaponDurability);
	}
}
