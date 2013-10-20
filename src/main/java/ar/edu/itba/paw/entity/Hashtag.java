package ar.edu.itba.paw.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.google.common.base.Strings;

@Entity
public class Hashtag extends PersistentEntity {

	private String tagName;

	@OneToOne
	private Twatt firstTwatt;

	@OneToMany
	private List<Twatt> twatts;

	public Hashtag(Twatt firstTweet, String tagName) {
		this.firstTwatt = firstTweet;
		this.tagName = tagName;
		twatts = new LinkedList<Twatt>();
	}

	public Twatt getFirstTwatt() {
		return this.firstTwatt;
	}

	public String getTagName() {
		return tagName;
	}

	public List<Twatt> getTwatts() {
		return twatts;
	}
	
	public void addTwatt(Twatt twatt){
		twatts.add(twatt);
	}

	public boolean isValid() {
		return !Strings.isNullOrEmpty(this.getTagName())
				&& this.getFirstTwatt() != null;

	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Hashtag))
			return false;

		Hashtag hashtag = (Hashtag) o;

		if (!firstTwatt.equals(hashtag.firstTwatt))
			return false;
		if (!tagName.equals(hashtag.tagName))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = firstTwatt.hashCode();
		result = 31 * result + tagName.hashCode();
		return result;
	}
}
