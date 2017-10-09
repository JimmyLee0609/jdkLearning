package loader;

public class MyService implements ServiceInterface {

	@Override
	public void sayHello() {
		System.out.println("hey man");
	}

	@Override
	public void sayHelloAgain() {
		System.out.println(System.currentTimeMillis());
	}

}
