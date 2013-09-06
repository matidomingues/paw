package ar.edu.itba.paw.model;

import org.joda.time.DateTime;

public class Tweet extends PersistableEntity{

    private User creator;
    private String message;
    private boolean deleted;
    private DateTime timestamp;

    public Tweet(Integer id, User creator, String message, boolean deleted, DateTime timestamp) {
        super(id);
        this.creator = creator;
        this.message = message;
        this.deleted = deleted;
        this.timestamp = timestamp;
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
}
