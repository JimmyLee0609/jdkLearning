package mxbean.beans;

public interface MxBeanSampleMXBean {
//	=====attributes======
	int getMax();
	void setMax(int max);
	String getTemp();
	void setTemp(String temp);
	OtherType getType();
	void setType(OtherType type);
	
//	======operations=================
	void reset();
	String print(String detail);
}
