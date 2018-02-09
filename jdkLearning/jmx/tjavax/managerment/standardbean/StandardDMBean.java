package tjavax.managerment.standardbean;

public interface StandardDMBean {
	//attribute
		boolean isTraceOn();
		public void setTraceOn(boolean traceOn);
		boolean isDebugOn();
		int getNumberOfResets();
//		operations
		void enableTracing();
		void disableTracing();
		void enableDebugging();
		void disableDebugging();
		void reset();
}
