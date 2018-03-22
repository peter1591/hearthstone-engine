package engine;

import java.util.ArrayList;

import engine.GameAction.BoardEntityIndex;
import engine.utils.CopyAsBaseByDeepCopy;
import engine.utils.DeepCopyable;

/**
 * Including context when performing an action.
 * @author petershih
 *
 */
public class FlowContext implements DeepCopyable<FlowContext>, CopyAsBaseByDeepCopy<FlowContext> {
	// playing card
	// target
	
	public static final int MAX_EVENT_INVOKE_DEPTH = 100;
	public int eventInvokeDepth = 0;
	
	public GameAction gameAction;
	public int attacker;
	public int defender;
	
	// for attack, this records the defenders
	// for spells, this records the targetables
	public ArrayList<BoardEntityIndex> targets;

	private FlowContext() {
		
	}
	
	static public FlowContext create() {
		FlowContext ret = new FlowContext();
		return ret;
	}
	
	@Override
	public FlowContext deepCopy() {
		FlowContext ret = new FlowContext();
		return ret;
	}
}
