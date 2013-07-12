package com.engine.action;

import java.util.ArrayList;

import com.engine.ui.GLNode;

/**
 * 把N个action合起来执行
 * @author YCMO
 *
 */
public class TimeLine extends AnimationAction{

	protected ArrayList<Action> pushActions = new ArrayList<Action>();
	GLNode tar;
	@Override
	public void setTarget(GLNode actor) {
		// TODO Auto-generated method stub
		tar = actor;
	}

	@Override
	public GLNode getTarget() {
		// TODO Auto-generated method stub
		return tar;
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		if (pushActions != null) {
			if (pushActions.size() > 0) {
				Action action = pushActions.get(0);
				action.act(delta);
				if (action.isDone()) {
					action.finish();
					pushActions.remove(0);
					if (pushActions.size() > 0) {
						Action next = pushActions.get(0);
						next.setTarget(tar);
					}else {
						done = true;
					}
				}
			}
		}
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return done;
	}

	@Override
	public Action copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 把N个action 顺序执行 比如先执行moveto 再执行 delay 或者别的action
	 * 
	 * @param action
	 */
	public void pushAction(Action action) {
		action.setTarget(tar);
		pushActions.add(action);
	}


}
