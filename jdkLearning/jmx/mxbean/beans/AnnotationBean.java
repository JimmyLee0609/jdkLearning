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
	// ��CompositeData�ع���Դ���͵ķ�ʽ2����Щ������Ӧ��������Ҫ��getter����
	@ConstructorProperties({ "Bbt", "Note" })
	public AnnotationBean(int bbt, String note) {
		this.bbt = bbt;
		this.note = note;
	}

	// ��CompositeData�ع���Դ���͵ķ���3 ��Ҫgetter�����Զ���setter����
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

	// ��CompositeData�ع���Դ���͵ķ�ʽ1��������ʱ���þ�̬������ת��
	public static AnnotationBean from(CompositeData cd) {
		int bbt = (int) cd.get("Bbt");
		String note = (String) cd.get("Note");
		return new AnnotationBean(bbt, note);
	}

	@Override
	public CompositeData toCompositeData(CompositeType ct) {
//		ʵ��CompositeDataView�ӿڣ���������ת��
//		�����ʵ��˼·��ֱ�ӽ�������Լ�Ĭ��ֵ������
//		Ȼ���鴫����type�͹�����type�Ƿ�ƥ�䣬ƥ�䷵��data��ƥ�䣬�������쳣�ͷ���null�����߻��Ƿ���������Data���У���Ҫ��
		String[] itemNames = new String[] {"Bbt","Note"};
		Object[] itemValues = new Object[] {5,"nonono"};
		OpenType<?>[] itemTypes = new OpenType[] {SimpleType.INTEGER,SimpleType.STRING};
		for(int i=0;i<itemNames.length;i++) {
		OpenType<?> type = ct.getType(itemNames[i]);
		boolean value = itemTypes[i].isValue(type);
		if(value) System.out.println("���Ͳ���");return null;
		}
		CompositeType type=null;
		CompositeData data=null;
		try {
			type=new CompositeType("mxbean.beans.AnnotationBean", "���м���C����", itemNames, itemNames, itemTypes);
			data=new CompositeDataSupport(ct, itemNames, itemValues);
		} catch (OpenDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return data;
	}

	@Override
	public String testPara(StandardMxBeanSample bean) {
		// ����������һ��MXbean,�ڵ��õ�ʱ����Ҫ������MXBean����ͬһ����������
		// TODO Auto-generated method stub
		return "����һ��";
	}

}
