package CompositeTest;

public class SecondLevelDecorator extends Decorator {
	
	Decorator dec;
	
	public SecondLevelDecorator(Decorator dec) {
		this.dec = dec;
	}

}
