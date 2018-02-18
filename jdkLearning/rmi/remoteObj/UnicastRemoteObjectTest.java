package remoteObj;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class UnicastRemoteObjectTest {

	@SuppressWarnings({ "unused", "static-access" })
	public static void main(String[] args) throws Exception {
		//UnicastRemoteObject����Ҳ��һ��remoteServer��
//		Remote���󣬼̳���������ڱ�¶�Լ��ÿͻ����ܹ����ýӿڵķ���
		
		// ��ȡ��ǰ�߳���������Զ�̷������õĿͻ����������ַ�����ʽ
		String clientHost = UnicastRemoteObject.getClientHost();
		// ��ȡ��־�������
		PrintStream log = UnicastRemoteObject.getLog();

		// ������Ϊ�������ݵ�Զ�̶���obj�Ĵ���� �˲������ڶ����ѵ��������Ч��
		Remote stub = UnicastRemoteObject.toStub(new RemoteMyBook());

		// ʹ��ָ���Ķ˿ڣ���¶remote����ʹ��RMI�Ŀͻ��˿��Ե���ָ����remote�ӿڷ���
		Remote exportObject = UnicastRemoteObject.exportObject(stub, 5959);

		// ��RMI��runtime���Ƴ�ָ���Ķ���
		UnicastRemoteObject.unexportObject(stub, false);

		RMIClientSocketFactory rsf = new RMIClientSocketFactory() {
			@Override
			public Socket createSocket(String host, int port) throws IOException {
				return null;
			}
		};
		RMIServerSocketFactory ssf = new RMIServerSocketFactory() {
			@Override
			public ServerSocket createServerSocket(int port) throws IOException {
				return null;
			}
		};
		// ָ���˿ڣ� �ͻ��ˣ��������˵��׽��ֹ�����¶remote����
		Remote exportObject2 = UnicastRemoteObject.exportObject(stub, 5959, rsf, ssf);
	}

}
