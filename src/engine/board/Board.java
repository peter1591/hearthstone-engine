package engine.board;

import engine.utils.CopyAsBaseByDeepCopy;
import engine.utils.DeepCopyable;

/**
 * Saves the mapping to the entity id.
 * 
 * @author petershih
 *
 */
public class Board implements DeepCopyable<Board>, CopyAsBaseByDeepCopy<Board> {
	int board; // boardEntityId
	Player[] players;

	public enum PlayerId {
		UNKNOWN(-1), FIRST(0), SECOND(1);

		private int index;

		PlayerId(int index) {
			this.index = index;
		}

		public int getIndex() {
			return this.index;
		}
	}

	private Board() {
	}

	static public Board create(int boardEntityid) {
		Board ret = new Board();
		ret.board = boardEntityid;
		ret.players = new Player[2];
		for (int i = 0; i < 2; ++i) {
			ret.players[i] = Player.create();
		}
		return ret;
	}

	@Override
	public Board deepCopy() {
		Board ret = new Board();
		ret.board = board;
		ret.players = new Player[2];
		for (int i = 0; i < 2; ++i) {
			ret.players[i] = players[i].deepCopy();
		}
		return ret;
	}

	public Player first() {
		return players[0];
	}

	public Player second() {
		return players[1];
	}

	public Player get(PlayerId playerId) {
		return players[playerId.getIndex()];
	}
	
	public int getBoardEntityId() {
		return board;
	}
}
