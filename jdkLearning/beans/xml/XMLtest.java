package xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import bean.MyBean;

public class XMLtest {

	public static void main(String[] args) throws Exception {
		writeObj();
		FileInputStream in = new FileInputStream("./beans/xml/out.xml");
		XMLDecoder decoder = new XMLDecoder(in);
		Object readObject = decoder.readObject();
		MyBean cast = MyBean.class.cast(readObject);
int id = cast.getId();
System.out.println(id);


	}

	private static void writeObj() throws FileNotFoundException {
		FileOutputStream out = new FileOutputStream(new File("./beans/xml/out.xml"));
		XMLEncoder xmlEncoder = new XMLEncoder(out, "gbk", false,2);
		MyBean myBean = new MyBean();
		xmlEncoder.writeObject(myBean);
		xmlEncoder.close();
	}

}
