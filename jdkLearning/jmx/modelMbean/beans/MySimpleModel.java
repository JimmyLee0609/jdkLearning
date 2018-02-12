package modelMbean.beans;

public class MySimpleModel {
	String name = "first Name";
	Integer[] mmp = new Integer[] { 5, 8, 9, 6, 3 };

	public MySimpleModel(String name, Integer[] mmp) {
		super();
		this.name = name;
		this.mmp = mmp;
	}

	public Integer[] getMmp() {
		return mmp;
	}

	public void setMmp(Integer[] mmp) {
		this.mmp = mmp;
	}

	public String getName() {
		return name;
	}

	public String setName(String name) {
		this.name = name;
		return name;
	}

	public MySimpleModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int mul(double one,double two) {
		return (int)(one+two);
	}
	public void reset() {
		add();
	}

	private void add() {
		System.out.println("add............");
	}

	@Override
	public String toString() {
		return "MySimpleModelMBean [name=" + name + ", ids=" + "]";
	}

}
