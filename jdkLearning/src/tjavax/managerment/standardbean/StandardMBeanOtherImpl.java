package tjavax.managerment.standardbean;

import javax.management.StandardMBean;

public class StandardMBeanOtherImpl extends StandardMBean implements StandardBeanInterface {
//	至少公开一个构造器
	public StandardMBeanOtherImpl() {
		super(StandardBeanInterface.class,false);
	}

	@Override
	public boolean isTraceOn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDebugOn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNumberOfResets() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void enableTracing() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disableTracing() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableDebugging() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disableDebugging() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
