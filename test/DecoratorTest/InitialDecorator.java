package DecoratorTest;

public class InitialDecorator extends Decorator {
	
	Decorator dec;
	
	public InitialDecorator(Decorator dec) {
		this.dec = dec;
	}

}
