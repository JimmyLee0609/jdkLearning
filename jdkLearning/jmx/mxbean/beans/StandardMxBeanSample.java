package mxbean.beans;

import java.beans.ConstructorProperties;

public class StandardMxBeanSample implements MxBeanSampleMXBean {
	int max;
	String temp;
	OtherType type;
	
	public OtherType getType() {
		return type;
	}

	public void setType(OtherType type) {
		this.type = type;
	}

	@Override
	public int getMax() {
		return max;
	}

	@Override
	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public String getTemp() {
		// TODO Auto-generated method stub
		return temp;
	}

	@Override
	public void setTemp(String temp) {
		this.temp = temp;
	}

	// ======cons=========
	public StandardMxBeanSample() {
	}

	@ConstructorProperties({ "max", "temp" })
	public StandardMxBeanSample(int max, String temp) {
		this.max = max;
		this.temp = temp;
	}

	// =======operations=======
	@Override
	public void reset() {
		System.out.println("reset");
		
		type=new OtherType("我的新类型");
	}

	@Override
	public String print(String detail) {
		System.out.println(detail);
		return detail;
	}

}
