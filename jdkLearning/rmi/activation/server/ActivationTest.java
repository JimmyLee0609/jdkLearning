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
	 * 首先使用 start rmiregistry rmid -J-Djava.security.policy=路径(设定策略文件的路径)
	 * 这个是必须要写的指定policy文件来限定ExecPermissions/ExecOptionPermissions，下面是可选的
	 * -J-Dexaamples.activation.policy=路径(这是一个属性，可以用于指定group的属性，也可能需要匹配上面的策略文件)
	 * -J-Dsun.rmi.log.debug=true 打开debug的日志模式
	 * -J-Dsun.rmi.server.activation.debugExec=true 打开debug模式
	 * -J-Djava.rmi.server.useCodebaseOnly=true 只是用指定的代码库 -port 1098 指定端口
	 */
	/*
	 * 比方说策略文件中写着,就是在rmid装载activation group的时候命令行允许使用的参数 permission
	 * com.sun.rmi.rmid.ExecOptionPermission
	 * "-Djava.security.policy=${examples.activation.policy}"; 限定rmid在装载activation
	 * group的时候可用的命令只能是Java，要添加的话可以继续添加路径下的文件，可用通配符* permission
	 * com.sun.rmi.rmid.ExecPermission
	 * "d:\\files\\apps\\java\\jdk1.7.0\\win\\bin\\java";
	 */
	public static void main(String[] args) throws Exception {
		// 启动上面的参数后就可以获取ActivationSystem，因为是sun的实现，没法改，或者直接实现ActivationSystem
		ActivationSystem system = ActivationGroup.getSystem();
		// 这个方法实际是用了，首先需要将ActivationSystem注册到registry中
		// Naming.lookup("//:port/java.rmi.activation.ActivationSystem")
		Remote lookup = Naming.lookup("rmi://127.0.0.1:1098/java.rmi.activation.ActivationSystem");
		// ====这个命令需要测试什么用======
		CommandEnvironment environment = new ActivationGroupDesc.CommandEnvironment("file:\\D:\\Program Files\\Java\\jdk1.8.0_162\\jre\\bin\\java", new String[] {});

		// 这里的属性会被rmid的策略文件限定可用的属性，不写属性内容也行。
		Properties prer = new Properties();
//		prer.setProperty("java.security.policy"," file:\\D:\\github_repository\\jdkLearning\\jdkLearning\\rmi\\activation\\server\\ActivationTest.policy");
		// 在jvm外的其他进程开启新的jvm来启动activationgroup，也可以是null
		// 构建一个activationGroup描述
		ActivationGroupDesc desc = new ActivationGroupDesc(prer, null);
		
		// 将其延迟加载的组注册到ActivationSystem中
		ActivationGroupID registerGroup = system.registerGroup(desc);
		ActivationGroupID currentGroupID = ActivationGroup.currentGroupID();
		ActivationSystem system2 = registerGroup.getSystem();
		
//		Account_Impl account_Impl = new Account_Impl("file:\\D:\\github_repository\\jdkLearning\\jdkLearning\\bin\\activation\\remoteObj",null,false, 0);
		// 保存一个对象的序列化信息
		MarshalledObject marshalledObject = new MarshalledObject("");
		// 构建延迟加载的信息 groupid 全类名 这个类的路径 restart mode
		ActivationDesc activationDesc = new ActivationDesc(registerGroup, "activation.remoteObj.Account",
				"file:\\D:\\github_repository\\jdkLearning\\jdkLearning\\bin\\activation\\remoteObj",
				marshalledObject, false);
		// 对象的序列化信息
		// 使用Activatable来注册延迟加载信息
		Remote register = Activatable.register(activationDesc);
		
		/*ActivationID exportObject = Activatable.exportObject(register,
				"file:\\\\D:\\\\github_repository\\\\jdkLearning\\\\jdkLearning\\\\bin\\\\activation\\\\remoteObj",
				marshalledObject, false, 0);*/
		// 完成绑定
		LocateRegistry.getRegistry().rebind("name", register);

		// 获得ActivationSystem后就可以正常注册了

		String string = system.toString();

	}

}
	 
 
 