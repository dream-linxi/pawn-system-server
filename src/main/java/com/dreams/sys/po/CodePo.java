package com.dreams.sys.po;

public class CodePo {
	private String value;
	private Long time;
	
	public CodePo(String value, Integer second) {
		super();
		this.value = value;
		this.time = System.currentTimeMillis() + second * 1000;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	
}
