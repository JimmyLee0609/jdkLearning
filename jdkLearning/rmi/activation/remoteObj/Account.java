package activation.remoteObj;

import java.rmi.Remote;

public interface Account extends Remote{
public int add(int one ,int two) ;
public int sub(int one , int two);
}
