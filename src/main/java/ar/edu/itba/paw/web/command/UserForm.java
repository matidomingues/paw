package ar.edu.itba.paw.web.command;

import org.apache.commons.fileupload.FileItem;
import org.joda.time.DateTime;

import ar.edu.itba.paw.model.User;

public class UserForm {

	private User user;
	private String username;
	private String name;
	private String surname;
	private String password;
	private String extrapassword;
	private String description;
	private String secretquestion;
	private String secretanswer;
	private FileItem photo;

	public UserForm(){
	}
	
	public UserForm(User user){
		this.user = user;
		this.username = user.getUsername();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.password = user.getPassword();
		this.extrapassword = user.getPassword();
		this.description = user.getDescription();
		this.secretquestion = user.getSecretQuestion();
		this.secretanswer = user.getSecretAnswer();
	}
	
	public User getUser() {
		return user;
	}
	public String getUsername() {
		return username;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getPassword() {
		return password;
	}
	public String getExtrapassword() {
		return extrapassword;
	}
	public String getDescription() {
		return description;
	}
	public String getSecretquestion() {
		return secretquestion;
	}
	public String getSecretanswer() {
		return secretanswer;
	}
	public FileItem getPhoto() {
		return photo;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setExtrapassword(String extrapassword) {
		this.extrapassword = extrapassword;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setSecretquestion(String secretquestion) {
		this.secretquestion = secretquestion;
	}
	public void setSecretanswer(String secretanswer) {
		this.secretanswer = secretanswer;
	}
	public void setPhoto(FileItem photo) {
		this.photo = photo;
	}
	public User build(){
		byte[] img = (photo == null)? new byte[0]:photo.get();
		if(user == null){
			return new User(username, password, name, surname, description, DateTime.now(), secretquestion, secretanswer, img);
		}else{
			user.setUsername(username);
			user.setName(name);
			user.setSurname(surname);
			user.setPassword(password);
			user.setDescription(description);
			user.setSecretQuestion(secretquestion);
			user.setSecretAnswer(secretanswer);
			user.setPhoto(img);
			return user;
		}
	}
}