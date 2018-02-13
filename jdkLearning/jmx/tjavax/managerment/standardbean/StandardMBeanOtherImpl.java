package tjavax.managerment.standardbean;

import javax.management.StandardMBean;

public class StandardMBeanOtherImpl extends StandardMBean implements StandardDMBean {
	public boolean isTraceOn() {
		return TraceOn;
	}
	public boolean isDebugOn() {
		return DebugOn;
	}
	private boolean TraceOn;

	private boolean DebugOn;
	private int NumberOfResets;
//	至少公开一个构造器
	public StandardMBeanOtherImpl() {
		super(StandardDMBean.class,false);
	}
@Override
public void setTraceOn(boolean traceOn) {
	TraceOn=traceOn;
	
}
@Override
public int getNumberOfResets() {
	// TODO Auto-generated method stub
	return NumberOfResets;
}
//=========operation===========
@Override
public void enableTracing() {
	// TODO Auto-generated method stub
	TraceOn=true;
	System.out.println("enableTracing");
}

@Override
public void disableTracing() {
	// TODO Auto-generated method stub
	TraceOn=false;
	System.out.println("disableTracing");
}

@Override
public void enableDebugging() {
	// TODO Auto-generated method stub
	DebugOn=true;
	System.out.println("enableDebugging");
}

@Override
public void disableDebugging() {
	// TODO Auto-generated method stub
	DebugOn=false;
	System.out.println("disableDebugging");
}

@Override
public void reset() {
	// TODO Auto-generated method stub
	TraceOn=false;
	DebugOn=false;
	System.out.println("reset");
}
@Override
public void setNumberOfResets(int num) {
this.NumberOfResets=num;	
}
float dd;
@Override
public float getDd() {
	// TODO Auto-generated method stub
	return dd;
}
@Override
public void setDd(float dd) {
this.dd=dd;	
}
@Override
public void add(float f) {
	// TODO Auto-generated method stub
	
}

}
