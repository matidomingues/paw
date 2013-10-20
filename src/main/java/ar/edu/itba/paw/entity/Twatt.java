package ar.edu.itba.paw.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;

@Entity
public class Twatt extends PersistentEntity {

	private String message;
	private DateTime timestamp;
	private boolean deleted;

	@ManyToOne
	private User creator;

	@OneToMany
	private List<Hashtag> hashtags;

	Twatt() {
	}

	public Twatt(String message, User user) {
		this.message = message;
		this.creator = user;
		this.timestamp = DateTime.now();
		this.deleted = false;
		this.hashtags = new LinkedList<Hashtag>();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Hashtag> getHashtags() {
		return this.hashtags;
	}

	public void addHashtag(Hashtag hashtag) {
		hashtags.add(hashtag);
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	/* Necesario para los jsp */
	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted() {
		this.deleted = true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Twatt))
			return false;

		Twatt twatt = (Twatt) o;

		if (!creator.equals(twatt.creator))
			return false;
		if (!message.equals(twatt.message))
			return false;
		if (!timestamp.equals(twatt.timestamp))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = creator.hashCode();
		result = 31 * result + message.hashCode();
		result = 31 * result + timestamp.hashCode();
		return result;
	}

}
