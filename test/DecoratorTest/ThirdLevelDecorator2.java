package DecoratorTest;

public class ThirdLevelDecorator2 extends Decorator2 {
	
	Decorator2 dec;
	
	public ThirdLevelDecorator2(Decorator2 dec) {
		this.dec = dec;
	}

}
