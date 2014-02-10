package ar.edu.itba.paw.web.pages.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.notification.FollowingNotification;
import ar.edu.itba.paw.domain.notification.MentionNotification;
import ar.edu.itba.paw.domain.notification.Notification;
import ar.edu.itba.paw.domain.notification.RetwattNotification;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.web.commons.TwattApplicationException;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.twatt.TwattPanel;

public class NotificationPage extends SecuredPage {

	private static final long serialVersionUID = -5356698688142943781L;

	@SuppressWarnings("serial")
	public NotificationPage(final IModel<TwattUser> userModel) {
		add(new RefreshingView<Notification>("notificationsList") {
			@Override
			protected Iterator<IModel<Notification>> getItemModels() {
				List<IModel<Notification>> notifications = new ArrayList<IModel<Notification>>();
				for(Notification notification : userModel.getObject().getNotifications()) {
					notifications.add(new EntityModel<Notification>(Notification.class, notification));
				}
				return notifications.iterator();
			}

			@Override
			protected void populateItem(Item<Notification> item) {
				if (item.getModelObject() instanceof RetwattNotification) {
					item.add(new TwattPanel("notification", 
							new EntityModel<Twatt>(Twatt.class, ((RetwattNotification)item.getModelObject()).getRetwatt()), userModel));
				} else if(item.getModelObject() instanceof MentionNotification) {
					item.add(new TwattPanel("notification", 
							new EntityModel<Twatt>(Twatt.class, ((MentionNotification)item.getModelObject()).getTwatt()), userModel));
				} else if (item.getModelObject() instanceof FollowingNotification) {
					item.add(new Label("notification", 
							new StringResourceModel("followingNotificationMessage", 
									Model.of(((FollowingNotification)item.getModelObject()).getFollower().getUsername()))));
				} else {
					throw new TwattApplicationException("Unexpected notification type");
				}				
			}
		});
	}
}
