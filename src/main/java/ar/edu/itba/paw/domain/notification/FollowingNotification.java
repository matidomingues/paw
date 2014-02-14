package ar.edu.itba.paw.domain.notification;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.util.Assert;

import ar.edu.itba.paw.domain.twattuser.TwattUser;

@Entity
public class FollowingNotification extends Notification {

    @ManyToOne
    private TwattUser follower;

    FollowingNotification() { /*XXX: Needed by hibernate */}

    public FollowingNotification(TwattUser recipient, TwattUser follower) {
        super(recipient);
        Assert.notNull(follower);
        this.follower = follower;
    }

    public TwattUser getFollower() {
        return this.follower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowingNotification)) return false;
        if (!super.equals(o)) return false;

        FollowingNotification that = (FollowingNotification) o;

        if (!follower.equals(that.follower)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + follower.hashCode();
        return result;
    }
}
