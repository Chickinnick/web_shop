package org.oa.tp.data;

import com.google.gson.annotations.SerializedName;

public class Author {
	
	@SerializedName("id")
	private final long id;
	
	@SerializedName("firstName")
	private String firstName;
	
	@SerializedName("lastName")
	private String lastName;
	
	@SerializedName("age")
	private int age;
	
	@SerializedName("gender")
	private String gender;

    public Author(String firstName, String lastName, int age, String gender) {
        
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }
	
	
        
	
	public Author(long id, String firstName, String lastName, int age,
			String gender) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getId() {
		return id;
	}
	
	
	

}
