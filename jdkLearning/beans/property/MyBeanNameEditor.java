package property;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyEditor;

import bean.MyBean;

public class MyBeanNameEditor implements PropertyEditor {
	public MyBeanNameEditor() {
		// TODO Auto-generated constructor stub
	}

	MyBean source;
	String value;

	public MyBeanNameEditor(MyBean source) {
		this.source = source;
	}

	@Override
	public void setValue(Object value) {
		sup.firePropertyChange("name", this.value, value);
		this.value = (String) value;
		source.setName(getAsText());
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public boolean isPaintable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void paintValue(Graphics gfx, Rectangle box) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getJavaInitializationString() {
		// TODO Auto-generated method stub
		return "????";
	}

	@Override
	public String getAsText() {
		return value;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text);
	}

	@Override
	public String[] getTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getCustomEditor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsCustomEditor() {
		// TODO Auto-generated method stub
		return false;
	}

	PropertyChangeSupport sup = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		sup.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		sup.removePropertyChangeListener(listener);
	}

}
