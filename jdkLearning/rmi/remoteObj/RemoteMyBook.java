package remoteObj;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteMyBook extends UnicastRemoteObject implements Book {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemoteMyBook() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getIsbn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIsbn(String isbn) {
		// TODO Auto-generated method stub

	}

}
