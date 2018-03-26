package engine;

import engine.board.Board;
import engine.entity.Entity;
import engine.entity.EntityManager;
import engine.event.FlowEventManager;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;

public class State implements DeepCopyable<State>, CopyableAsBase<State> {
	EntityManager entityManager;
	Board board;
	FlowEventManager flowEventManager;
	FlowContext flowContext;
	
	private State() {
	}
	
	static public State create() {
		State ret = new State();
		ret.entityManager = EntityManager.create();
		
		int boardEntityId = ret.entityManager.add(Entity.create());
		ret.board = Board.create(boardEntityId);
		
		ret.flowEventManager = FlowEventManager.create();
		ret.flowContext = FlowContext.create();
		return ret;
	}
	
	@Override
	public State deepCopy() {
		State ret = new State();
		ret.entityManager = entityManager.deepCopy();
		ret.board = board.deepCopy();
		ret.flowEventManager = flowEventManager.deepCopy();
		ret.flowContext = flowContext.deepCopy();
		return ret;
	}
	
	@Override
	public State copyAsBase() {
		State ret = new State();
		ret.entityManager = entityManager.copyAsBase();
		ret.board = board.copyAsBase();
		ret.flowEventManager = flowEventManager.copyAsBase();
		ret.flowContext = flowContext.copyAsBase();
		return ret;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public FlowEventManager getFlowEventManager() {
		return flowEventManager;
	}
	
	public FlowContext getFlowContext() {
		return flowContext;
	}
}
