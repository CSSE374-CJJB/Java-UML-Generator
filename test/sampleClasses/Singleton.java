package sampleClasses;

public class Singleton {
	
	private static Singleton instance = new Singleton();
	
	public static Singleton getInstance() {
		return instance;
	}

}
