package ar.edu.itba.paw.web.command;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ar.edu.itba.paw.domain.twattuser.TwattUser;

public class UserForm {

	private TwattUser twattUser;
	private String username;
	private String name;
	private String surname;
	private String password;
	private String extrapassword;
	private String description;
	private String secretquestion;
	private String secretanswer;
	private CommonsMultipartFile photo;

	public UserForm(){
	}
	
	public UserForm(TwattUser user){
		this.twattUser = user;
		this.username = user.getUsername();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.password = user.getPassword();
		this.extrapassword = user.getPassword();
		this.description = user.getDescription();
		this.secretquestion = user.getSecretQuestion();
		this.secretanswer = user.getSecretAnswer();
	}
	
	public TwattUser getTwattUser() {
		return twattUser;
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
	public CommonsMultipartFile getPhoto() {
		return photo;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setTwattUser(TwattUser user) {
		this.twattUser = user;
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
	public void setPhoto(CommonsMultipartFile photo) {
		this.photo = photo;
	}
	public TwattUser build(){
		byte[] img = (photo == null)? new byte[0]:photo.getBytes();
		if(twattUser == null){
			return new TwattUser(username, password, name, surname, description, secretquestion, secretanswer, img);
		}else{
			if(username != null){
				twattUser.setUsername(username);
			}
			twattUser.setName(name);
			twattUser.setSurname(surname);
			twattUser.setPassword(password);
			twattUser.setDescription(description);
			if(secretquestion != null){
				twattUser.setSecretQuestion(secretquestion);
			}
			if(secretanswer != null){
				twattUser.setSecretAnswer(secretanswer);
			}
			twattUser.setPhoto(img);
			return twattUser;
		}
	}
}
