package engine.state.board;

import engine.utils.CopyAsBaseByDeepCopy;
import engine.utils.DeepCopyable;

/**
 * Saves the mapping to the entity index.
 * 
 * @author petershih
 *
 */
public class Board implements DeepCopyable<Board>, CopyAsBaseByDeepCopy<Board> {
	int board;
	Player[] players;
	
	private Board() {
	}
	
	static public Board create() {
		Board ret = new Board();
		ret.players = new Player[2];
		for (int i=0; i<2; ++i) {
			ret.players[i] = Player.create();
		}
		return ret;
	}
	
	@Override
	public Board deepCopy() {
		Board ret = new Board();
		ret.players = new Player[2];
		for (int i=0; i<2; ++i) {
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
}
