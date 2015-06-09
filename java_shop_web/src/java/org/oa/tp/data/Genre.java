package org.oa.tp.data;

import com.google.gson.annotations.SerializedName;

public class Genre {

	@SerializedName("id")
	private final long id;
	
	@SerializedName("name")
	private String name;

        
        
        
    public Genre(String name) {
        this.id = 0;
        this.name = name;
    }

        
        
	public Genre(long id, String name) {
		
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}
	
	
}
