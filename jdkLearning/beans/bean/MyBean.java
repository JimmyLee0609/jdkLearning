package bean;

import java.beans.PropertyChangeSupport;
import java.beans.beancontext.BeanContext;
import java.beans.beancontext.BeanContextServices;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MyBean implements Serializable {

	private static final long serialVersionUID = 3145980918000583685L;

	private String name="";
	private int id=9;
	public MyBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyBean(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public void testExe() {
		System.out.println("testExe");
	}
	
	@Override
	public String toString() {
		return "MyBean [name=" + name + ", id=" + id + "]";
	}
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		id=stream.readInt();
		name=(String) stream.readObject();
	}
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeInt(id);
		stream.writeObject(name);
	}
}
