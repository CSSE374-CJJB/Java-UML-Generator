package sampleClasses;

public class ThirdLevelDecorator extends Decorator {
	
	Decorator dec;
	
	public ThirdLevelDecorator(Decorator dec) {
		this.dec = dec;
	}

}
