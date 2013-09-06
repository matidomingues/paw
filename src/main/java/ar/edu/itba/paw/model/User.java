package ar.edu.itba.paw.model;

import org.joda.time.DateTime;

import java.util.List;

public class User extends PersistableEntity{

	private String username;
	private String password;
	private String name;
	private String surname;
	private String description;
    private boolean enabled;
	private DateTime date;
	private List<Twatt> tweets;
	
	public User(Integer id, String username, String password, String name, String surname, String description, DateTime date){
        super(id);
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.description = description;
        this.enabled = enabled;
        this.date = date;
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

    public DateTime getDate() {
        return date;
    }

    public void setDescription(String description) {
		this.description = description;
	}


	public String getUsername() {
		return username;
	}
	
	public List<Twatt> getTweets(){
		return tweets;
	}

    public boolean isEnabled() {
        return enabled;
    }
}
