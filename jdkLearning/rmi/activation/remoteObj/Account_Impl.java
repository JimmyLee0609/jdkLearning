package activation.remoteObj;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationID;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;

public class Account_Impl  extends Activatable implements Account,Serializable{
	public Account_Impl(ActivationID id, int port) throws RemoteException {
		super(id, port);
		// TODO Auto-generated constructor stub
	}
	public Account_Impl(ActivationID id, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf)
			throws RemoteException {
		super(id, port, csf, ssf);
		// TODO Auto-generated constructor stub
	}
	public Account_Impl(String location, MarshalledObject<?> data, boolean restart, int port,
			RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws ActivationException, RemoteException {
		super(location, data, restart, port, csf, ssf);
		// TODO Auto-generated constructor stub
	}
	public Account_Impl(String location, MarshalledObject<?> data, boolean restart, int port)
			throws ActivationException, RemoteException {
		super(location, data, restart, port);
		// TODO Auto-generated constructor stub
	}



	int b = 10;
	private static final long serialVersionUID = 844359345494111644L;
	@Override
	public int add(int one, int two) {
		return 0;
	}
	@Override
	public int sub(int one, int two) {
		return 0;
	}
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeInt(b);
	}
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		stream.defaultReadObject();
		b = stream.readInt();
	}
}
