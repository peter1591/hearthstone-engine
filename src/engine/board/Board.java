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
	PlayerId currentPlayer;
	Player[] players;
	int turn;

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
		ret.currentPlayer = PlayerId.UNKNOWN;
		ret.players = new Player[2];
		ret.turn = 0;
		for (int i = 0; i < 2; ++i) {
			ret.players[i] = Player.create();
		}
		return ret;
	}

	@Override
	public Board deepCopy() {
		Board ret = new Board();
		ret.board = board;
		ret.currentPlayer = currentPlayer;
		ret.players = new Player[2];
		ret.turn = turn;
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
	
	public PlayerId getCurrentPlayerId() {
		return currentPlayer;
	}
	
	public Player getCurrentPlayer() {
		return get(currentPlayer);
	}
	
	public int getBoardEntityId() {
		return board;
	}
	
	public void switchCurrentPlayer() {
		if (currentPlayer == PlayerId.FIRST) {
			currentPlayer = PlayerId.SECOND;
		}
		else {
			currentPlayer = PlayerId.FIRST;
		}
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public void increaseTurn() {
		this.turn++;
	}
}
