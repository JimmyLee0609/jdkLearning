package lang;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
@Resource
public class ClassDomain extends Thread implements Comparator<ClassDomain> {
List<String> list=new ArrayList<String>();
public static int b=0;
String c="gsdg";
static int [] gg=new int[]{1,25,6};
	@Override
	public int compare(ClassDomain o1, ClassDomain o2) {
		return 0;
	}
	int add(){return 0;}
	public void asserta(){
		assert(false);
	}
public ClassDomain(int c,String b){}
public ClassDomain(){}
}
