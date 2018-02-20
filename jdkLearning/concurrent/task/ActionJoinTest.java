package task;

import java.util.UUID;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class JoinTest extends RecursiveAction {
	private static final long serialVersionUID = -5570523850857332903L;
String name="";

	public JoinTest(String name) {
	super();
	this.name = name;
}
 static int index=3;
	@SuppressWarnings("unused")
	@Override
	protected void compute() {
		System.out.println("compute");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
