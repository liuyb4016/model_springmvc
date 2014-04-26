package cn.liuyb.app.common.utils;

public class MutableData<T> {

	T value;
	
	public MutableData(T value) {
		setValue(value);
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
}
