package engine.flow;

import engine.ManagedState;
import engine.board.Board.PlayerId;
import engine.board.ReadablePlayer;
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
		public boolean handle(Event event, ManagedState state, EventArgument argument) {
			PlayerId side = argument.owner.getFinalProperty().getSide();
			if (state.getPlayer(side).hasWeapon()) {
				int weaponId = state.getPlayer(side).getWeaponEntityId();
				int attack = state.getEntityProperty(weaponId).getAttack();
				argument.owner.getMutableProperty().addAttack(attack);
			}
			return true;
		}
	});

	static final LambdaEventHandler reduceWeaponDurability = LambdaEventHandler.create(new Operation() {
		@Override
		public boolean handle(Event event, ManagedState state, EventArgument argument) {
			PlayerId side = argument.owner.getFinalProperty().getSide();
			if (state.getPlayer(side).hasWeapon()) {
				int weaponId = state.getPlayer(side).getWeaponEntityId();
				int damage = 1;
				state.damage(state.getBoardEntityId(), weaponId, damage);
			}
			return true;
		}
	});

	public static void Process(ManagedState state) {
		ProcessPlayer(state, state.getPlayer(PlayerId.FIRST));
		ProcessPlayer(state, state.getPlayer(PlayerId.SECOND));
	}

	static void ProcessPlayer(ManagedState state, ReadablePlayer player) {
		state.addEvent(player.getHeroEntityId(), Event.PROPERTY_MODIFIERS, addWeaponAttack);
		state.addEvent(player.getHeroEntityId(), Event.AFTER_ATTACKED, reduceWeaponDurability);
	}
}
