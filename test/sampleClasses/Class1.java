package sampleClasses;

public class Class1 implements Inter1{

	public int publicField;
	private int privateField;
	private Class2 clazz;
	
	public Class1() {
		this.clazz = new Class2();
	}
	
	@Override
	public void publicVoidMethod(){
		this.clazz.protectedVoidMethod();
		privateReturnIntMethod();
	}
	
	private int privateReturnIntMethod() {
		return privateField;
	}
	
	public Class2 getClass2() {
		return null;
	}
	
}
