package sampleClasses;

public class Class1 implements Inter1{

	public int publicField;
	private int privateField;
	
	@Override
	public void publicVoidMethod(){
		privateReturnIntMethod();
	}
	
	private int privateReturnIntMethod() {
		return privateField;
	}
	
	public Class2 getClass2() {
		return null;
	}
	
}
