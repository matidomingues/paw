package ar.edu.itba.paw.domain.notification;

import ar.edu.itba.paw.domain.repository.AbstractHibernateRepo;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class HibernateNotificationRepo extends AbstractHibernateRepo<Notification> implements NotificationRepo{


    @Override
    public Notification find(int notification_id) {
        if (notification_id < 0) {
            throw new IllegalArgumentException("Invalid Id");
        }
        List<Notification> notifications = this.find("from Notification where id = ?", notification_id);
        if (notifications.isEmpty()) {
            return null;
        }
        return notifications.get(0);
    }

    @Override
    public Notification find(Notification notification) {
        Assert.notNull(notification);

        List<Notification> notifications = this.find("from Notification n where n = ?", notification);
        if (notifications.isEmpty()) {
            return  null;
        }
        return notifications.get(0);
    }
}
