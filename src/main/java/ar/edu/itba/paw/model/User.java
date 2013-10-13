package ar.edu.itba.paw.model;

import org.joda.time.DateTime;

public class User extends PersistableEntity {

	private String username;
	private String password;
	private String name;
	private String surname;
	private String description;
	private boolean enabled;
	private DateTime date;
	private String secretQuestion;
	private String secretAnswer;
    private byte[] photo;

	public User(Integer id, String username, String password, String name,
			String surname, String description, DateTime date,
			String secretQuestion, String secretAnswer, byte[] photo) {
		super(id);
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.description = description;
		this.enabled = enabled;
		this.date = date;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
        this.photo = photo;
	}

	public boolean checkPassword(String password){
		return password.equals(this.password);
	}
	
    public void setUsername(String username) {
		this.username = username;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public User(String username, String password, String name,
                String surname, String description, DateTime date,
                String secretQuestion, String secretAnswer) {
        this(username, password, name, surname, description, date, secretQuestion, secretAnswer, new byte[0]);
    }

    public User(String username, String password, String name,
			String surname, String description, DateTime date,
			String secretQuestion, String secretAnswer, byte[] photo){
		this(-1, username, password, name, surname, description, date, secretQuestion, secretAnswer, photo);
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
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

	public boolean isEnabled() {
		return enabled;
	}

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (enabled != user.enabled) return false;
        if (!date.equals(user.date)) return false;
        if (!description.equals(user.description)) return false;
        if (!name.equals(user.name)) return false;
        if (!password.equals(user.password)) return false;
        if (!secretAnswer.equals(user.secretAnswer)) return false;
        if (!secretQuestion.equals(user.secretQuestion)) return false;
        if (!surname.equals(user.surname)) return false;
        if (!username.equals(user.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + date.hashCode();
        result = 31 * result + secretQuestion.hashCode();
        result = 31 * result + secretAnswer.hashCode();
        return result;
    }

}
