package core;

public class Shared<T> {

	T value;
	
	public Shared(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	public void set(T value) {
		this.value = value;
	}
	
}
