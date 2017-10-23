package io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class SerializableDomain implements Serializable{

	private static final long serialVersionUID = 5623419030379328817L;

	int id=4;
	transient boolean br=false;
	public String name="testname";
	ArrayList<Date> list=new ArrayList<Date>();
	
	public SerializableDomain(int id, boolean br, String name) {
		super();
		this.id = id;
		this.br = br;
		this.name = name;
	}
	public SerializableDomain() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isBr() {
		return br;
	}
	public void setBr(boolean br) {
		this.br = br;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Date> getList() {
		return list;
	}
	public void setList(ArrayList<Date> list) {
		this.list = list;
	} 
	
}
