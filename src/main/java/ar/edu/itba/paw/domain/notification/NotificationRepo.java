package ar.edu.itba.paw.domain.notification;

import java.io.Serializable;

public interface NotificationRepo {

    Serializable save(Notification notification);

    Notification find(int notification_id);

    Notification find(Notification notification);

}
