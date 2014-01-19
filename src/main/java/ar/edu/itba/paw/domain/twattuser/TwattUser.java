package ar.edu.itba.paw.domain.twattuser;

import ar.edu.itba.paw.domain.entity.PersistentEntity;
import ar.edu.itba.paw.domain.notification.FollowingNotification;
import ar.edu.itba.paw.domain.notification.Notification;
import ar.edu.itba.paw.domain.twatt.Twatt;
import com.google.common.base.Strings;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

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

    @Column(nullable = false)
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime date;

    @Column(nullable = false)
	private boolean enabled;
	
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] photo;

	@OneToMany(mappedBy="creator", cascade=CascadeType.ALL)
	private List<Twatt> twatts;

	@ManyToMany
	private List<TwattUser> followers = new ArrayList<TwattUser>();
	
	@ManyToMany(mappedBy="followers", cascade=CascadeType.ALL)
	private List<TwattUser> followings = new ArrayList<TwattUser>();

    @ManyToMany
    private Set<Twatt> favourites = new HashSet<Twatt>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private Set<Notification> notifications = new HashSet<Notification>();
	
	private boolean privacy;
	
	private long access;

	TwattUser() {
	}

	public TwattUser(String username, String name, String surname, String password,
			String description, String secretquestion, String secretanswer,
			byte[] photo) {
        Assert.hasText(username);
        Assert.hasText(name);
        Assert.hasText(surname);
        Assert.hasText(password);
        Assert.hasText(description);
        Assert.hasText(secretquestion);
        Assert.hasText(secretanswer);
        Assert.notNull(photo);

        this.username = username;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.description = description;
		this.secretQuestion = secretquestion;
		this.secretAnswer = secretanswer;
		this.photo = photo;
		this.twatts = new LinkedList<Twatt>();
		this.date = new DateTime();
		this.enabled = true;
		this.privacy = true;
		this.access = (long) 0;
	}

	public DateTime getDate() {
		return date;
	}
	public Long getAccess(){
		return this.access;
	}
	
	public void setAccess(Long access){
        Assert.notNull(access);
		this.access = access;
	}
	
	public void addAccess(){
		this.access++;
	}
	
	public boolean getPrivacy(){
		return privacy;
	}
	
	public void setPrivacy(boolean status){
		this.privacy = status;
	}
	
	public List<TwattUser> getFollowers(){
		return followers;
	}
	
	public List<TwattUser> getFollowings(){
		return followings;
	}
	
	private void addFollower(TwattUser user){
        Assert.notNull(user);
		this.followers.add(user);
        this.notify(new FollowingNotification(this, user));
	}
	
	public void addFollowing(TwattUser user){
        Assert.notNull(user);
		if(!followings.contains(user)){
			user.addFollower(this);
			this.followings.add(user);
		}
	}
	
	private void removeFollower(TwattUser user){
        Assert.notNull(user);
		this.followers.remove(user);
	}
	
	public void removeFollowing(TwattUser user){
        Assert.notNull(user);
		user.removeFollower(this);
		this.followings.remove(user);
	}

	public void setDate(DateTime date) {
        Assert.notNull(date);
		this.date = date;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void addTwatt(Twatt twatt) {
        Assert.notNull(twatt);
		twatts.add(twatt);
	}

	public List<Twatt> getTwatts() {
		return twatts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
        Assert.hasText(username);
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
        Assert.hasText(name);
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
        Assert.hasText(surname);
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
        Assert.hasText(password);
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
        Assert.hasText(description);
		this.description = description;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
        Assert.hasText(secretQuestion);
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretanswer) {
        Assert.hasText(secretanswer);
		this.secretAnswer = secretanswer;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
        Assert.notNull(photo);
		this.photo = photo;
	}

	public boolean checkPassword(String pass){
        Assert.hasText(pass);
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

	public boolean isFollowedBy(TwattUser user){
        Assert.notNull(user);
		return followers.contains(user);
	}

    public void addFavourite(Twatt favorite) {
        Assert.notNull(favorite);
        this.favourites.add(favorite);
    }

    public void removeFavourite(Twatt favourite) {
        Assert.notNull(favourite);
        this.favourites.remove(favourite);
    }

    public boolean isFavourite(Twatt twatt) {
        Assert.notNull(twatt);
        return this.favourites.contains(twatt);
    }

    public void notify(Notification notification) {
        Assert.notNull(notification);
        this.notifications.add(notification);
    }

    public Set<Notification> getNotifications() {
        return this.notifications;
    }

    public Set<Notification> getUnreadNotifications() {
        Set<Notification> unreads = Collections.EMPTY_SET;
        for(Notification notification : this.getNotifications()) {
            if (!notification.isRead()) {
                unreads.add(notification);
            }
        }
        return unreads;
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

    public Set<Twatt> getFavourites() {
        return favourites;
    }
}
