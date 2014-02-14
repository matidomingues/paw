package ar.edu.itba.paw.domain.notification;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.util.Assert;

import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;

@Entity
public class MentionNotification extends Notification {

    @ManyToOne
    private Twatt twatt;

    MentionNotification() { /*XXX: Needed by hibernate */}

    public MentionNotification(TwattUser recipient, Twatt twatt) {
        super(recipient);
        Assert.notNull(twatt);
        this.twatt = twatt;
    }

    public Twatt getTwatt() {
        return this.twatt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MentionNotification)) return false;
        if (!super.equals(o)) return false;

        MentionNotification that = (MentionNotification) o;

        if (!twatt.equals(that.twatt)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + twatt.hashCode();
        return result;
    }
}
