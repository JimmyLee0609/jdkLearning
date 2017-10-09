package loader;

public class FirstServiceTest implements ServiceInterface {

	@Override
	public void sayHello() {
		System.out.println(getClass()+"  -sayHello");
	}

	@Override
	public void sayHelloAgain() {
		System.out.println("again  -"+getClass());
	}

}
