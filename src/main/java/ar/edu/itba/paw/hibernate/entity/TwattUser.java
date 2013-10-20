package ar.edu.itba.paw.hibernate.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;

import com.google.common.base.Strings;

@Entity
public class TwattUser extends PersistentEntity {

	@Column(nullable=false,unique=true)
	private String username;
	
	private String name;
	private String surname;
	private String password;
	private String description;
	private String secretQuestion;
	private String secretAnswer;
	private DateTime date;
	private boolean enabled;
	
	@Lob
	private byte[] photo;

	@OneToMany(mappedBy="creator", cascade=CascadeType.ALL)
	private List<Twatt> twatts;

	TwattUser() {
	}

	public TwattUser(String username, String name, String surname, String password,
			String description, String secretquestion, String secretanswer,
			byte[] photo) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.description = description;
		this.secretQuestion = secretquestion;
		this.secretAnswer = secretanswer;
		this.photo = photo;
		this.twatts = new LinkedList<Twatt>();
		this.date = DateTime.now();
		this.enabled = true;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void addTwatt(Twatt twatt) {
		twatts.add(twatt);
	}

	public List<Twatt> getTwatts() {
		return twatts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretanswer) {
		this.secretAnswer = secretanswer;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public boolean checkPassword(String pass){
		return getPassword().equals(pass);
	}
	
	public boolean isValidUser() {
		return !Strings.isNullOrEmpty(this.getUsername())
				&& !Strings.isNullOrEmpty(this.getPassword())
				&& !Strings.isNullOrEmpty(this.getName())
				&& !Strings.isNullOrEmpty(this.getDescription())
				&& !Strings.isNullOrEmpty(this.getSurname())
				&& !Strings.isNullOrEmpty(this.getSecretQuestion())
				&& !Strings.isNullOrEmpty(this.getSecretAnswer())
				&& this.getDate() != null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TwattUser))
			return false;

		TwattUser user = (TwattUser) o;

		if (enabled != user.enabled)
			return false;
		if (!date.equals(user.date))
			return false;
		if (!description.equals(user.description))
			return false;
		if (!name.equals(user.name))
			return false;
		if (!password.equals(user.password))
			return false;
		if (!secretAnswer.equals(user.secretAnswer))
			return false;
		if (!secretQuestion.equals(user.secretQuestion))
			return false;
		if (!surname.equals(user.surname))
			return false;
		if (!username.equals(user.username))
			return false;

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
