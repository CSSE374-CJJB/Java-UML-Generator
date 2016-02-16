package DecoratorTest;

public class SecondLevelDecorator2 extends Decorator2 {
	
	Decorator2 dec;
	
	public SecondLevelDecorator2(Decorator2 dec) {
		this.dec = dec;
	}

}
