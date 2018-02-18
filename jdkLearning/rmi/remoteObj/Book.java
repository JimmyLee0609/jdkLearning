package remoteObj;

import java.io.Serializable;
import java.rmi.Remote;

public interface Book extends Remote, Serializable {


	public String getIsbn() ;

	public void setIsbn(String isbn) ;

}
