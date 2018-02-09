package tjavax.managerment.standardbean;

public class StandardD implements StandardDMBean {
private boolean TraceOn=true;

private boolean DebugOn;
private int NumberOfResets;
public StandardD() {}
public StandardD(boolean trace,boolean debug) {}
	@Override
	public boolean isTraceOn() {
		// TODO Auto-generated method stub
		return TraceOn;
	}

	public void setTraceOn(boolean traceOn) {
		TraceOn = traceOn;
	}
	@Override
	public boolean isDebugOn() {
		// TODO Auto-generated method stub
		return DebugOn;
	}

	@Override
	public int getNumberOfResets() {
		// TODO Auto-generated method stub
		return NumberOfResets;
	}
//==============operations======================
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

}
