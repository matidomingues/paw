package ar.edu.itba.paw.domain.hashtag;

import ar.edu.itba.paw.domain.entity.PersistentEntity;
import ar.edu.itba.paw.domain.twatt.Twatt;
import com.google.common.base.Strings;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Hashtag extends PersistentEntity {

	@Column(nullable=false,unique=true)
	private String tagName;

	@OneToOne
	private Twatt firstTwatt;

	@ManyToMany
	private List<Twatt> twatts;

	Hashtag() {
	}

	public Hashtag(Twatt firstTweet, String tagName) {
        Assert.notNull(firstTweet);
        Assert.hasText(tagName);

        this.firstTwatt = firstTweet;
		this.tagName = tagName;
		twatts = new LinkedList<Twatt>();
        this.addTwatt(this.firstTwatt);
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

	public void addTwatt(Twatt twatt) {
        Assert.notNull(twatt);
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
