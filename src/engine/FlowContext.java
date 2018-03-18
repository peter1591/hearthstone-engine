package engine;

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
