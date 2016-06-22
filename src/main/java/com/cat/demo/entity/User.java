
package com.cat.demo.entity;

import com.cat.demo.annotation.Column;
import com.cat.demo.annotation.Id;
import com.cat.demo.annotation.Table;

@Table
public class User {

	@Id("uuid")
	private int uid;

	@Column("name")
	private String n;

	@Column
	private int age;

	@Column("address")
	private String addr;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", n=" + n + ", age=" + age + ", addr=" + addr + "]";
	}

}
