package ar.edu.itba.paw.model;

import java.util.List;

public class User extends PersistableEntity{

	private String username;
	private String password;
	private String name;
	private String surname;
	private String description;
    private boolean enabled;
	private List<Tweet> tweets;
	
	public User(Integer id, String username, String password, String name, String surname, String description, boolean enabled){
        super(id);
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.description = description;
        this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getUsername() {
		return username;
	}
	
	public List<Tweet> getTweets(){
		return tweets;
	}

    public boolean isEnabled() {
        return enabled;
    }
}
