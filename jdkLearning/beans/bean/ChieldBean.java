package bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ChieldBean extends MyBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5645275100997006135L;
	private String myname = "myname";

	@Override
	public String toString() {
		return "ChieldBean [myname=" + myname + "]";
	}

	public ChieldBean() {
		super();
	}

	public ChieldBean(String name, int id, String myname) {
		super(name, id);
		this.myname = myname;
	}

	public String getMyname() {
		return myname;
	}

	public void setMyname(String myname) {
		this.myname = myname;
	}
private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
	stream.defaultReadObject();
	myname=(String)stream.readObject();
}
private void writeObject(ObjectOutputStream stream) throws IOException {
	stream.defaultWriteObject();
	stream.writeObject(myname);
}
}
