package property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;

import bean.MyBean;

public class PropertyEditorManagerTest {

	public static void main(String[] args) {
//		��ȡ����editor��·��
		String[] editorSearchPath = PropertyEditorManager.getEditorSearchPath();//sun.beans.editors
//		�趨����editor��·��
		PropertyEditorManager.setEditorSearchPath(editorSearchPath);
//		ע��ָ�����͵����Ա༭��
		PropertyEditorManager.registerEditor(MyBean.class, PropertyEditorSupport.class);
//		����ָ�����͵����Ա༭������Ҫʹ����Ҫ���Ա�����࣬���Ǳ༭��,��Щ����swing������
		
		MyBean bean = new MyBean();
//		�༭��support���ǻ�����Դ��һ��value����һ��list�ļ������������ã��������Լ�ʵ��propertyEditor��ʵ��
		PropertyEditorSupport sup = new PropertyEditorSupport(bean);
		
		PropertyEditor findEditor2 = PropertyEditorManager.findEditor(PropertyEditorSupport.class);//null
		
//		����ʹ�÷��佫������manager�е�ָ�����Ӧ��editorʵ����
		PropertyEditor editor = PropertyEditorManager.findEditor(MyBean.class);//PropertyEditorSupport
//		editor��Ӽ�����������fireChange��ʱ�򣬽����еļ�����������֪ͨ
		editor.addPropertyChangeListener(null);
		
		
//=============ʵ��һ���༭���ı�ָ�����һ�����Բ��������Ա����ʱ�򷢳�֪ͨ======================
		//�ܱ�Ť��ÿ�����Զ���Ҫ�ö�Ӧ�ı༭��
		String name = bean.getName();
		MyBeanNameEditor editor2 = new MyBeanNameEditor(bean);
		editor2.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("property change"+evt);
			}
		});
		
		editor2.setValue("nanananannanana");
		String name2 = bean.getName();
		
		System.out.println();
	}

}
