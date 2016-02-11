package GeneralTests;

public class Class2 extends Class1 {

	protected int protectedInt;
	int defaultInt;
	
	public Class1 clazz = new Class1();
	
	protected void protectedVoidMethod() {
		this.clazz.getClass2();
	}
	
}
