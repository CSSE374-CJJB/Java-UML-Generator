package DecoratorTest;

public class InitialDecorator2 extends Decorator2 {
	
	Decorator2 dec;
	
	public InitialDecorator2(Decorator2 dec) {
		this.dec = dec;
	}

}
