package observable;

import java.util.Observable;
import java.util.Observer;

public class ObservableTest {

	public static void main(String[] args) {
		// 观察者模式的 被观察者 需要它的子类才可以使用setChange()方法 才能通知观察者
		// 这个类一般不用的理解观察者模式的代码写法
		Observable observable = new Observable(); // 新建被观察者 通知者
		// 新建观察者
		Observer observer = new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				// 观察者中的回调函数
				System.out.println("i just update");
			}
		};
		observable.addObserver(observer); // 添加观察者，将观察者保存在通知者的一个容器中
		int countObservers = observable.countObservers(); // 计算观察者数量
		boolean hasChanged = observable.hasChanged(); // 被观察者状态是否改变
		// 没有setChange直接return
		observable.notifyObservers(observer); // 通知指定的观察者，通知，保存观察者的容器中的指定观察者，调用它的回调函数
		observable.notifyObservers(); // 通知所有的观察者
		observable.deleteObserver(observer); // 删除指定观察者
		observable.deleteObservers(); // 删除所有观察者
		String string = observable.toString();
	}

}
