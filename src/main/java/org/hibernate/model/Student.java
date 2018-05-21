package org.hibernate.model;

import java.util.Date;

public class Student {
	private long id;// 学号
    private String username;// 姓名
    private Date birthday;// 生日
    private String sex;// 性别
	
    public Student(long id, String username, Date birthday, String sex) {
		this.id = id;
		this.username = username;
		this.birthday = birthday;
		this.sex = sex;
	}

	public Student() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
