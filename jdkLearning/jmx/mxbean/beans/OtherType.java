package mxbean.beans;

public class OtherType {
String type;

public OtherType(String type) {
	super();
	this.type = type;
}

public OtherType() {
	super();
	// TODO Auto-generated constructor stub
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

@Override
public String toString() {
	return "OtherType [type=" + type + "]";
};

}
