package serialization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class SerializableDomain implements Serializable{

	
	public SerializableDomain(int id, boolean br, String name) {
		super();
		this.id = id;
		this.br = br;
		this.name = name;
		
	}
	public SerializableDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 5623419030379328817L;

	int id=4;
	transient boolean br=false;
	public String name="testname";
	ArrayList<Date> list=new ArrayList<Date>();
	
	
}
