package ar.edu.itba.paw.model;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

public class Twatt extends PersistableEntity{

    private User creator;
    private String message;
    private boolean deleted;
    private DateTime timestamp;

    public Twatt(User creator, String message, boolean deleted, DateTime timestamp) {
        this(-1, creator, message, deleted, timestamp);
    }
    public Twatt(Integer id, User creator, String message, boolean deleted, DateTime timestamp) {
        super(id);
        this.creator = creator;
        this.message = message;
        this.deleted = deleted;
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
		this.message = message;
	}

	public User getCreator() {
        return creator;
    }

    public String getMessage() {
        return message;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public boolean isDeleted() {
        return deleted;
    }

    /* Necesario para los jsp */
    public boolean getDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Twatt)) return false;

        Twatt twatt = (Twatt) o;

        if (!creator.equals(twatt.creator)) return false;
        if (!message.equals(twatt.message)) return false;
        if (!timestamp.equals(twatt.timestamp)) return false;

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
