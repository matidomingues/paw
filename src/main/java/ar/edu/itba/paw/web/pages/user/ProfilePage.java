package ar.edu.itba.paw.web.pages.user;


import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.twatt.TwattPanel;

public class ProfilePage extends SecuredPage{

	private static final long serialVersionUID = -2553312957172341808L;
	@SpringBean private UserRepo userRepo;
	
	@SuppressWarnings("serial")
	public ProfilePage(final IModel<TwattUser> userModel, final IModel<TwattUser> viewerModel){

		if(!userModel.getObject().equals(viewerModel.getObject())){
			userModel.getObject().addAccess();
		}
		final IModel<List<Twatt>> myTwattsModel = new LoadableDetachableModel<List<Twatt>>() {
			@Override
			protected List<Twatt> load() {
				return viewerModel.getObject().getTwatts();
			}			
		};
		ListView<Twatt> twattListView = new PropertyListView<Twatt>("twatts", myTwattsModel) {
			@Override
			protected void populateItem(ListItem<Twatt> item) {
				item.add(new TwattPanel("twatt", item.getModel(), viewerModel));				
			}
		};
		
		add(twattListView);
		Link<Void> follow = new Link<Void>("follow") {
			@Override
			public void onClick() {
				userModel.getObject().addFollowing(viewerModel.getObject());
			}
		};
		Link<Void> unfollow = new Link<Void>("unfollow"){
			@Override
			public void onClick() {
				userModel.getObject().removeFollowing(viewerModel.getObject());				
			}
		};
		if (userModel.getObject().isFollowedBy(viewerModel.getObject())) {
			follow.setEnabled(false);
			follow.setVisible(false);
		} else {
			unfollow.setEnabled(false);
			unfollow.setVisible(false);
		}
		add(follow);
		add(unfollow);
		add(new Label("username", userModel.getObject().getUsername()));
		add(new Label("description", userModel.getObject().getDescription()));
		add(new Label("name", userModel.getObject().getName()));
		add(new Label("surname", userModel.getObject().getSurname()));
		add(new Label("accesses", Long.toString(userModel.getObject().getAccess())));
		add(new Label("followers", Integer.toString(userModel.getObject().getFollowers().size())));
		add(new Label("followings", Integer.toString(userModel.getObject().getFollowings().size())));
		
	}
}
