package ar.edu.itba.paw.domain.twatt;

import ar.edu.itba.paw.domain.entity.PersistentEntity;
import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
@Entity
public class Twatt extends PersistentEntity {

    @Column(nullable = false)
	private String message;

    @Column(nullable = false)
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime timestamp;

    @Column(nullable = false)
	private boolean deleted;

	@ManyToOne
	private TwattUser creator;

	@OneToMany(mappedBy="twatts", cascade=CascadeType.ALL)
	private List<Hashtag> hashtags;

	Twatt() {
	}

	public Twatt(String message, TwattUser user) {
		this.message = message;
		this.creator = user;
		this.timestamp = new DateTime();
		this.deleted = false;
		this.hashtags = new LinkedList<Hashtag>();
		user.addTwatt(this);
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

	public TwattUser getCreator() {
		return creator;
	}

	public void setCreator(TwattUser creator) {
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
