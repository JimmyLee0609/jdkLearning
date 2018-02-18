package listener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MyPropertyChangeListener implements PropertyChangeListener{

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object source = evt.getSource();
		System.out.println("´¥·¢¼àÌýÆ÷"+evt.getSource());
	}

}
