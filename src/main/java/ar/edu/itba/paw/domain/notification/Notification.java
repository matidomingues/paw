package ar.edu.itba.paw.domain.notification;

import ar.edu.itba.paw.domain.entity.PersistentEntity;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Notification extends PersistentEntity {

    @ManyToOne
    private TwattUser recipient;

    private boolean read;

    Notification() { /*XXX: Needed by hibernate */}

    public Notification(TwattUser recipient) {
        Assert.notNull(recipient);
        this.recipient = recipient;
        this.read = false;
    }

    public TwattUser getRecipient() {
        return this.recipient;
    }

    public boolean isRead() {
        return this.read;
    }

    public boolean getRead() {
        return this.isRead();
    }

    public void setRead() {
        this.read = true;
    }

    public void setUnread() {
        this.read = false;
    }

    public void setRead(boolean value) {
        if (value) {
            this.setRead();
        } else {
            this.setUnread();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;

        Notification that = (Notification) o;

        if (read != that.read) return false;
        if (!recipient.equals(that.recipient)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recipient.hashCode();
        result = 31 * result + (read ? 1 : 0);
        return result;
    }
}
