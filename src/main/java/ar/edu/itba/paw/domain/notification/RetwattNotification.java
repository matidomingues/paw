package ar.edu.itba.paw.domain.notification;

import ar.edu.itba.paw.domain.twatt.Retwatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class RetwattNotification extends Notification {

    @ManyToOne
    private Retwatt retwatt;

    RetwattNotification() { /*XXX: Needed by hibernate */}

    public RetwattNotification(TwattUser recipient, Retwatt retwatt) {
        super(recipient);
        Assert.notNull(retwatt);
        this.retwatt = retwatt;
    }

    public Retwatt getRetwatt() {
        return this.retwatt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RetwattNotification)) return false;
        if (!super.equals(o)) return false;

        RetwattNotification that = (RetwattNotification) o;

        if (!retwatt.equals(that.retwatt)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + retwatt.hashCode();
        return result;
    }
}
