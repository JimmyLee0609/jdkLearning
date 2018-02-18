package property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;

import bean.MyBean;

public class PropertyEditorManagerTest {

	public static void main(String[] args) {
//		获取查找editor的路径
		String[] editorSearchPath = PropertyEditorManager.getEditorSearchPath();//sun.beans.editors
//		设定查找editor的路径
		PropertyEditorManager.setEditorSearchPath(editorSearchPath);
//		注册指定类型的属性编辑器
		PropertyEditorManager.registerEditor(MyBean.class, PropertyEditorSupport.class);
//		查找指定类型的属性编辑器，需要使用需要属性变更的类，不是编辑器,这些都是swing的内容
		
		MyBean bean = new MyBean();
//		编辑器support就是缓存了源，一个value，和一个list的监听器。不好用，倒不如自己实现propertyEditor来实现
		PropertyEditorSupport sup = new PropertyEditorSupport(bean);
		
		PropertyEditor findEditor2 = PropertyEditorManager.findEditor(PropertyEditorSupport.class);//null
		
//		就是使用反射将保存在manager中的指定类对应的editor实例化
		PropertyEditor editor = PropertyEditorManager.findEditor(MyBean.class);//PropertyEditorSupport
//		editor添加监听器，可以fireChange的时候，将所有的监听器都进行通知
		editor.addPropertyChangeListener(null);
		
		
//=============实现一个编辑器改变指定类的一个属性并且在属性变更的时候发出通知======================
		//很别扭，每个属性都需要用对应的编辑器
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
