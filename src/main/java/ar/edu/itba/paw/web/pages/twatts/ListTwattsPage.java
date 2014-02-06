package ar.edu.itba.paw.web.pages.twatts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.web.TwatterSession;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.pages.user.ProfilePage;

public class ListTwattsPage extends SecuredPage {

	public ListTwattsPage(final List<Twatt> twatts) {
		add(new FeedbackPanel("feedback"));
		add(new RefreshingView<Twatt>("twatts") {

			@Override
			protected Iterator<IModel<Twatt>> getItemModels() {
				List<IModel<Twatt>> result = new ArrayList<IModel<Twatt>>();
				for (Twatt t : twatts) {
					result.add(new EntityModel<Twatt>(Twatt.class, t));
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<Twatt> item) {
				item.add(new Link<Twatt>("userLink", item.getModel()) {
					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(
								new EntityModel<TwattUser>(TwattUser.class, getModelObject().getCreator()), 
								getViewer()));
					}
				}.add(new Label("creator.username")));
				item.add(new Label("timestamp"));
				item.add(new Label("message"));
			}
		});
	}

	protected TwatterSession getTwatterSession() {
		return (TwatterSession) getSession();
	}
}
