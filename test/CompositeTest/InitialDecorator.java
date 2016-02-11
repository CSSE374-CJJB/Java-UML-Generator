package CompositeTest;

public class InitialDecorator extends Decorator {
	
	Decorator dec;
	
	public InitialDecorator(Decorator dec) {
		this.dec = dec;
	}

}
