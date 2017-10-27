package observable;

import java.util.Observable;
import java.util.Observer;

public class ObservableTest {

	public static void main(String[] args) {
		// �۲���ģʽ�� ���۲��� ��Ҫ��������ſ���ʹ��setChange()���� ����֪ͨ�۲���
		// �����һ�㲻�õ����۲���ģʽ�Ĵ���д��
		Observable observable = new Observable(); // �½����۲��� ֪ͨ��
		// �½��۲���
		Observer observer = new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				// �۲����еĻص�����
				System.out.println("i just update");
			}
		};
		observable.addObserver(observer); // ��ӹ۲��ߣ����۲��߱�����֪ͨ�ߵ�һ��������
		int countObservers = observable.countObservers(); // ����۲�������
		boolean hasChanged = observable.hasChanged(); // ���۲���״̬�Ƿ�ı�
		// û��setChangeֱ��return
		observable.notifyObservers(observer); // ָ֪ͨ���Ĺ۲��ߣ�֪ͨ������۲��ߵ������е�ָ���۲��ߣ��������Ļص�����
		observable.notifyObservers(); // ֪ͨ���еĹ۲���
		observable.deleteObserver(observer); // ɾ��ָ���۲���
		observable.deleteObservers(); // ɾ�����й۲���
		String string = observable.toString();
	}

}
