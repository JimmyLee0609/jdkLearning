package util;

public class Domain implements Comparable<Domain>{
int index;
String name;
public int getIndex() {
	return index;
}
public void setIndex(int index) {
	this.index = index;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Domain(int index, String name) {
	super();
	this.index = index;
	this.name = name;
}
public Domain() {
	super();
	// TODO Auto-generated constructor stub
}
@Override
public String toString() {
	return "Domain [index=" + index + ", name=" + name + "]";
}
@Override
public int compareTo(Domain o) {
	
	return this.index-o.getIndex();
}

}
