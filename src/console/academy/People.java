package console.academy;

import java.io.Serializable;

public class People implements Serializable{
	//필드
	public String name;
	public int age;
	public String address;
	public String phone;
	//[기본 생성자]
	public People() {}
	//[인자 생성자]
	public People(String name, int age, String address, String phone) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.phone = phone;
	}
	//[멤버 메소드]
	public String get() {
		return String.format("이름:%s,나이:%s,주소:%s,연락처:%s",name,age,address,phone);
	} //get
	public void print() {
		System.out.println(get());
	} //print
	@Override
	public String toString() {
	    return String.format("이름: %s, 나이: %d, 주소: %s, 연락처: %s", name, age, address, phone);
	} //toString
	public String getName() {
	    return this.name;
	} //getName

	
} ////class
