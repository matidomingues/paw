package ar.edu.itba.paw.web.pages.user;


import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.twatt.TwattListPanel;

public class ProfilePage extends SecuredPage{

	private enum FollowActionOptions {
		FOLLOW,
		UNFOLLOW,
		NO_FOLLOW
	}
	
	private static final long serialVersionUID = -2553312957172341808L;
	public static final String USER_PARAMETER = "user";
	@SpringBean private UserRepo userRepo;
	
	public ProfilePage(PageParameters parameters) {
		populate(new EntityModel<TwattUser>(TwattUser.class, 
				userRepo.getUserByUsername(parameters.get(USER_PARAMETER).toString(""))));
	}
	

	public ProfilePage(IModel<TwattUser> userModel){
		populate(userModel);
	}

	@SuppressWarnings("serial")
	private void populate(final IModel<TwattUser> userModel) {
		final IModel<TwattUser> viewerModel = getViewer();
		boolean privateProfile = (userModel.getObject().getPrivacy() == false ||
				viewerModel.getObject() != null) ? false : true;
		FollowActionOptions followActionOptions;
		if(!userModel.getObject().equals(viewerModel.getObject())){
			userModel.getObject().addAccess();
		}
		if (userModel.getObject().equals(viewerModel.getObject())) {
			followActionOptions = FollowActionOptions.NO_FOLLOW;
		} else {
			if (userModel.getObject().isFollowedBy(viewerModel.getObject())) {
				followActionOptions = FollowActionOptions.UNFOLLOW;
			} else {
				followActionOptions = FollowActionOptions.FOLLOW;
			}
		}
		
		add(new WebMarkupContainer("privateProfileMessageContainer")
				.add(new Label("privateProfileMessage", getString("privateProfileMessage")))
				.setVisible(privateProfile));
		
		WebMarkupContainer profileContainer = new WebMarkupContainer("profileContainer");
		profileContainer.setVisible(!privateProfile);
		profileContainer.add(new TwattListPanel("twatts", TwattListPanel.convertList(userModel.getObject().getTwatts()), getViewer()));
		MarkupContainer followers = new Link<Void>("followers-link"){
			@Override
			public void onClick() {
				final IModel<List<TwattUser>> userFollowers = new LoadableDetachableModel<List<TwattUser>>() {
					@Override
					protected List<TwattUser> load() {
						return userModel.getObject().getFollowers();
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

		profileContainer.add(new Label("username", userModel.getObject().getUsername()));
		profileContainer.add(new Label("description", userModel.getObject().getDescription()));
		profileContainer.add(new Label("name", userModel.getObject().getName()));
		profileContainer.add(new Label("surname", userModel.getObject().getSurname()));
		profileContainer.add(new Label("accesses", Long.toString(userModel.getObject().getAccess())));
		profileContainer.add(followers);
		profileContainer.add(followings);
		profileContainer.add(followAction(userModel, viewerModel)
				.setVisible(followActionOptions == FollowActionOptions.FOLLOW));
		profileContainer.add(unfollowAction(userModel, viewerModel)
				.setVisible(followActionOptions == FollowActionOptions.UNFOLLOW));
		
		add(profileContainer);
		
	}

	@SuppressWarnings("serial")
	private Component followAction(final IModel<TwattUser> userModel, final IModel<TwattUser> viewerModel) {
		WebMarkupContainer followOption = new WebMarkupContainer("followOption");
		StringResourceModel followOptionHeader = new StringResourceModel("followOptionHeader", this, null);
		StringResourceModel followActionLabel = new StringResourceModel("followActionLabel", this, null);
		Link<Void> followAction = new Link<Void>("followAction") {
			@Override
			public void onClick() {
				//viewerModel.getObject().addFollowing(userModel.getObject());
				userRepo.getUserByUsername(viewerModel.getObject().getUsername())
					.addFollowing(userRepo.getUserByUsername(userModel.getObject().getUsername()));
			}
		};
		followOption.add(new Label("followOptionHeader", followOptionHeader));
		followOption.add(followAction.add(new Label("followActionLabel",followActionLabel)));
		return followOption;		
	}

	@SuppressWarnings("serial")
	private Component unfollowAction(final IModel<TwattUser> userModel, final IModel<TwattUser> viewerModel) {
		WebMarkupContainer unfollowOption = new WebMarkupContainer("unfollowOption");
		StringResourceModel followOptionHeader = new StringResourceModel("unfollowOptionHeader", this, null);
		StringResourceModel followActionLabel = new StringResourceModel("unfollowActionLabel", this, null);
		Link<Void> followAction = new Link<Void>("unfollowAction") {
			@Override
			public void onClick() {
				userRepo.getUserByUsername(viewerModel.getObject().getUsername())
					.removeFollowing(userRepo.getUserByUsername(userModel.getObject().getUsername()));
				//viewerModel.getObject().removeFollowing(userModel.getObject());
			}
		};
		unfollowOption.add(new Label("unfollowOptionHeader", followOptionHeader));
		unfollowOption.add(followAction.add(new Label("unfollowActionLabel",followActionLabel)));
		return unfollowOption;
	}
	
}
