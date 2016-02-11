package AdapterTest;

public class Core implements ToAdaptTo {
	
	ToAdapt t;
	
	public Core (ToAdapt t) {
		this.t = t;
	}

	@Override
	public void otherFirst() {
		this.t.first();
	}

	@Override
	public void otherSecond() {
		this.t.second();
	}

	@Override
	public void otherThird() {
		this.t.third();
	}

}
