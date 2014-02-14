package ar.edu.itba.paw.web.pages.userlist;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.userlist.UserList;
import ar.edu.itba.paw.domain.userlist.UserListRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.twatt.TwattListPanel;

public class FollowList extends SecuredPage {

	private static final long serialVersionUID = 2547121853198174158L;
	@SpringBean
	UserListRepo userListRepo;
	
	@SuppressWarnings("serial")
	public FollowList() {
		final IModel<TwattUser> viewerModel = getViewer();
		RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);
		for (UserList list : viewerModel.getObject().getLists()) {
			AbstractItem item = new AbstractItem(repeating.newChildId());
			repeating.add(item);
			final IModel<UserList> userList = new EntityModel<UserList>(UserList.class, list);
			item.add(new Label("name", list.getName()));
			Button deleteButton = new Button("remove") {
				@Override
	            public void onSubmit() {
					userListRepo.deleteList(userList.getObject());
					setResponsePage(new FollowList());
	            }
			};
			Button editButton = new Button("edit") {
				@Override
	            public void onSubmit() {
					setResponsePage(new EditFollowList(userList));
	            }
			};
			Form<?> actionForm = new Form<Void>("actionForm");
			actionForm.add(deleteButton);
			actionForm.add(editButton);
			item.add(actionForm);
			List<IModel<Twatt>> twattModels = new LinkedList<IModel<Twatt>>();
			for (TwattUser user :userList.getObject().getFollowed()) {
				for (Twatt twatt : user.getTwatts()) {
					twattModels.add(new EntityModel<Twatt>(Twatt.class, twatt));
				}
			}
			item.add(new TwattListPanel("followedTwatts", twattModels, viewerModel));
//			item.add(getRefreshingView(userList, viewerModel));
		}
	}
	
//	@SuppressWarnings("serial")
//	private RefreshingView<Twatt> getRefreshingView(final IModel<UserList> userList, final IModel<TwattUser> viewerModel){
//		RefreshingView<Twatt> view = new RefreshingView<Twatt>("followedTwatts"){
//
//			@Override
//			protected Iterator<IModel<Twatt>> getItemModels() {
//				List<IModel<Twatt>> twattModels = new LinkedList<IModel<Twatt>>();
//				for (TwattUser user :userList.getObject().getFollowed()) {
//					for (Twatt twatt : user.getTwatts()) {
//						twattModels.add(new EntityModel<Twatt>(Twatt.class, twatt));
//					}
//				}
//				return twattModels.iterator();
//			}
//
//			@Override
//			protected void populateItem(Item<Twatt> item) {
//				item.add(new TwattPanel("followedTwatt", item.getModel(), viewerModel));				
//			}
//			
//		};
//		return view;
//	}
}
