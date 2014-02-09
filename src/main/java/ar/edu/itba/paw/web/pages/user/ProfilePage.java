package ar.edu.itba.paw.web.pages.user;


import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.twatt.TwattPanel;

public class ProfilePage extends SecuredPage{

	private static final long serialVersionUID = -2553312957172341808L;
	public static final String USER_PARAMETER = "user";
	@SpringBean private static UserRepo userRepo;
	
	public ProfilePage(PageParameters parameters) {
		this(new EntityModel<TwattUser>(TwattUser.class, 
				userRepo.getUserByUsername(parameters.get(USER_PARAMETER).toString(""))));
	}
	
	@SuppressWarnings("serial")
	public ProfilePage(final IModel<TwattUser> userModel){
		final IModel<TwattUser> viewerModel = getViewer();
		if(!userModel.getObject().equals(viewerModel.getObject())){
			userModel.getObject().addAccess();
		}
		final IModel<List<Twatt>> myTwattsModel = new LoadableDetachableModel<List<Twatt>>() {
			@Override
			protected List<Twatt> load() {
				return userModel.getObject().getTwatts();
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
		if (userModel.getObject().equals(viewerModel.getObject())) {
			unfollow.setEnabled(false);
			unfollow.setVisible(false);
			follow.setEnabled(false);
			follow.setVisible(false);
		} else {
			if (userModel.getObject().isFollowedBy(viewerModel.getObject())) {
				follow.setEnabled(false);
				follow.setVisible(false);
			} else {
				unfollow.setEnabled(false);
				unfollow.setVisible(false);
			}
		}
		MarkupContainer followers = new Link<Void>("followers-link"){
			@Override
			public void onClick() {
				final IModel<List<TwattUser>> userFollowers = new LoadableDetachableModel<List<TwattUser>>() {
					@Override
					protected List<TwattUser> load() {
						return userModel.getObject().getFollowings();
					}
				};
				setResponsePage(new FindPage(userFollowers));
			}
		}.add(new Label("followers", Integer.toString(userModel.getObject().getFollowers().size())));
		
		MarkupContainer followings = new Link<Void>("followings-link"){
			@Override
			public void onClick() {
				final IModel<List<TwattUser>> userFollowings = new LoadableDetachableModel<List<TwattUser>>() {
					@Override
					protected List<TwattUser> load() {
						return userModel.getObject().getFollowings();
					}
				};
				setResponsePage(new FindPage(userFollowings));
			}
		}.add(new Label("followings", Integer.toString(userModel.getObject().getFollowings().size())));
		
		add(follow);
		add(unfollow);
		add(new Label("username", userModel.getObject().getUsername()));
		add(new Label("description", userModel.getObject().getDescription()));
		add(new Label("name", userModel.getObject().getName()));
		add(new Label("surname", userModel.getObject().getSurname()));
		add(new Label("accesses", Long.toString(userModel.getObject().getAccess())));
		add(followers);
		add(followings);
		
	}
}
