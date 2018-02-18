package activation.server;

import java.rmi.MarshalledObject;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationDesc;
import java.rmi.activation.ActivationGroup;
import java.rmi.activation.ActivationGroupDesc;
import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.rmi.activation.ActivationGroupID;
import java.rmi.activation.ActivationID;
import java.rmi.activation.ActivationSystem;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import activation.remoteObj.Account_Impl;

public class ActivationTest {

	@SuppressWarnings("unused")
	/*
	 * ����ʹ�� start rmiregistry rmid -J-Djava.security.policy=·��(�趨�����ļ���·��)
	 * ����Ǳ���Ҫд��ָ��policy�ļ����޶�ExecPermissions/ExecOptionPermissions�������ǿ�ѡ��
	 * -J-Dexaamples.activation.policy=·��(����һ�����ԣ���������ָ��group�����ԣ�Ҳ������Ҫƥ������Ĳ����ļ�)
	 * -J-Dsun.rmi.log.debug=true ��debug����־ģʽ
	 * -J-Dsun.rmi.server.activation.debugExec=true ��debugģʽ
	 * -J-Djava.rmi.server.useCodebaseOnly=true ֻ����ָ���Ĵ���� -port 1098 ָ���˿�
	 */
	/*
	 * �ȷ�˵�����ļ���д��,������rmidװ��activation group��ʱ������������ʹ�õĲ��� permission
	 * com.sun.rmi.rmid.ExecOptionPermission
	 * "-Djava.security.policy=${examples.activation.policy}"; �޶�rmid��װ��activation
	 * group��ʱ����õ�����ֻ����Java��Ҫ��ӵĻ����Լ������·���µ��ļ�������ͨ���* permission
	 * com.sun.rmi.rmid.ExecPermission
	 * "d:\\files\\apps\\java\\jdk1.7.0\\win\\bin\\java";
	 */
	public static void main(String[] args) throws Exception {
		// ��������Ĳ�����Ϳ��Ի�ȡActivationSystem����Ϊ��sun��ʵ�֣�û���ģ�����ֱ��ʵ��ActivationSystem
		ActivationSystem system = ActivationGroup.getSystem();
		// �������ʵ�������ˣ�������Ҫ��ActivationSystemע�ᵽregistry��
		// Naming.lookup("//:port/java.rmi.activation.ActivationSystem")
		Remote lookup = Naming.lookup("rmi://127.0.0.1:1098/java.rmi.activation.ActivationSystem");
		// ====���������Ҫ����ʲô��======
		CommandEnvironment environment = new ActivationGroupDesc.CommandEnvironment("file:\\D:\\Program Files\\Java\\jdk1.8.0_162\\jre\\bin\\java", new String[] {});

		// ��������Իᱻrmid�Ĳ����ļ��޶����õ����ԣ���д��������Ҳ�С�
		Properties prer = new Properties();
//		prer.setProperty("java.security.policy"," file:\\D:\\github_repository\\jdkLearning\\jdkLearning\\rmi\\activation\\server\\ActivationTest.policy");
		// ��jvm����������̿����µ�jvm������activationgroup��Ҳ������null
		// ����һ��activationGroup����
		ActivationGroupDesc desc = new ActivationGroupDesc(prer, null);
		
		// �����ӳټ��ص���ע�ᵽActivationSystem��
		ActivationGroupID registerGroup = system.registerGroup(desc);
		ActivationGroupID currentGroupID = ActivationGroup.currentGroupID();
		ActivationSystem system2 = registerGroup.getSystem();
		
//		Account_Impl account_Impl = new Account_Impl("file:\\D:\\github_repository\\jdkLearning\\jdkLearning\\bin\\activation\\remoteObj",null,false, 0);
		// ����һ����������л���Ϣ
		MarshalledObject marshalledObject = new MarshalledObject("");
		// �����ӳټ��ص���Ϣ groupid ȫ���� ������·�� restart mode
		ActivationDesc activationDesc = new ActivationDesc(registerGroup, "activation.remoteObj.Account",
				"file:\\D:\\github_repository\\jdkLearning\\jdkLearning\\bin\\activation\\remoteObj",
				marshalledObject, false);
		// ��������л���Ϣ
		// ʹ��Activatable��ע���ӳټ�����Ϣ
		Remote register = Activatable.register(activationDesc);
		
		/*ActivationID exportObject = Activatable.exportObject(register,
				"file:\\\\D:\\\\github_repository\\\\jdkLearning\\\\jdkLearning\\\\bin\\\\activation\\\\remoteObj",
				marshalledObject, false, 0);*/
		// ��ɰ�
		LocateRegistry.getRegistry().rebind("name", register);

		// ���ActivationSystem��Ϳ�������ע����

		String string = system.toString();

	}

}
	 
 
 