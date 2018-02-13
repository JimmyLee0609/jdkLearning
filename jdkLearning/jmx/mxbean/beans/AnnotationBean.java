package mxbean.beans;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeDataView;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class AnnotationBean implements AnnotationInterface, CompositeDataView {
	// 从CompositeData重构成源类型的方式2，这些参数对应的属性需要有getter方法
	@ConstructorProperties({ "Bbt", "Note" })
	public AnnotationBean(int bbt, String note) {
		this.bbt = bbt;
		this.note = note;
	}

	// 从CompositeData重构成源类型的方法3 需要getter的属性都有setter方法
	public AnnotationBean() {
	}

	int bbt = 99;
	String note = "gtresa";

	public int getBbt() {
		return bbt;
	}

	public void setBbt(int bbt) {
		this.bbt = bbt;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	// 从CompositeData重构成源类型的方式1，可以随时调用静态方法来转换
	public static AnnotationBean from(CompositeData cd) {
		int bbt = (int) cd.get("Bbt");
		String note = (String) cd.get("Note");
		return new AnnotationBean(bbt, note);
	}

	@Override
	public CompositeData toCompositeData(CompositeType ct) {
//		实现CompositeDataView接口，方便类型转换
//		这里的实现思路是直接将类的属性及默认值给到，
//		然后检查传来的type和构建的type是否匹配，匹配返回data不匹配，不能跑异常就返回null。或者还是返回正常的Data都行，看要求
		String[] itemNames = new String[] {"Bbt","Note"};
		Object[] itemValues = new Object[] {5,"nonono"};
		OpenType<?>[] itemTypes = new OpenType[] {SimpleType.INTEGER,SimpleType.STRING};
		for(int i=0;i<itemNames.length;i++) {
		OpenType<?> type = ct.getType(itemNames[i]);
		boolean value = itemTypes[i].isValue(type);
		if(value) System.out.println("类型不对");return null;
		}
		CompositeType type=null;
		CompositeData data=null;
		try {
			type=new CompositeType("mxbean.beans.AnnotationBean", "类中键的C类型", itemNames, itemNames, itemTypes);
			data=new CompositeDataSupport(ct, itemNames, itemValues);
		} catch (OpenDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return data;
	}

	@Override
	public String testPara(StandardMxBeanSample bean) {
		// 参数中是另一个MXbean,在调用的时候需要这两个MXBean都在同一个服务器中
		// TODO Auto-generated method stub
		return "测试一下";
	}

}
