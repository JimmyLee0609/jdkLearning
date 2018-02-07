package tjavax.managerment.standardbean;

public interface StandardBeanInterface {
	//attribute
		boolean isTraceOn();
		boolean isDebugOn();
		int getNumberOfResets();
//		operations
		void enableTracing();
		void disableTracing();
		void enableDebugging();
		void disableDebugging();
		void reset();
}
