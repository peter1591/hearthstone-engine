package engine.aura;

import java.util.HashSet;
import java.util.Set;

import engine.State;
import engine.board.Player;
import engine.board.Board.PlayerId;
import engine.entity.ReadableProperty;

public interface ForOtherFriendlyMinions extends AuraSpec {
	public static Set<Integer> getOtherFriendlyMinionss(int auraEmitter, State state) {
		ReadableProperty property = state.getEntityManager().get(auraEmitter).getFinalProperty();
		PlayerId side = property.getSide();

		Player player = state.getBoard().get(side);
		Set<Integer> affects = new HashSet<>();
		for (int i = 0; i < player.getMinionsCount(); ++i) {
			int minionEntityId = player.getMinionEntityId(i);
			if (minionEntityId == auraEmitter)
				continue;
			affects.add(minionEntityId);
		}

		return affects;
	}

	default Set<Integer> getTargets(int auraEmitter, State state) {
		return getOtherFriendlyMinionss(auraEmitter, state);
	}
}
