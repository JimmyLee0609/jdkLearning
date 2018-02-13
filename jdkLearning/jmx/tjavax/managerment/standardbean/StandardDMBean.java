package tjavax.managerment.standardbean;

import mxbean.beans.AnnotationBean;

public interface StandardDMBean {
	//attribute
		boolean isTraceOn();
		public void setTraceOn(boolean traceOn);
		boolean isDebugOn();
		int getNumberOfResets();
		void setNumberOfResets(int num);
		public float getDd();
		public void setDd(float dd);
//		operations
		void enableTracing();
		void disableTracing();
		void enableDebugging();
		void disableDebugging();
		void reset();
		void add(float f);
}
