package activation.server;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	public static void main(String[] args) throws Exception {
Registry registry = LocateRegistry.getRegistry();
Remote lookup = registry.lookup("name");
//Remote ll = registry.lookup("mmp");
Remote lookup2 = Naming.lookup("rmi://localhost:1098/java.rmi.activation.ActivationSystem");
Remote name = Naming.lookup("name");
//Remote mmp = Naming.lookup("mmp");

System.out.println(name);

	}

}
