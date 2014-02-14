package ar.edu.itba.paw.web.pages.userlist;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.domain.userlist.UserList;
import ar.edu.itba.paw.domain.userlist.UserListRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;

public class EditFollowList extends SecuredPage{

	private static final long serialVersionUID = 5848514157160922120L;

	@SpringBean
	UserRepo userRepo;
	
	@SpringBean
	UserListRepo userListRepo;
	
	private transient List<TwattUser> usersList;
	
	@SuppressWarnings("serial")
	public EditFollowList(final IModel<UserList> userList){
		add(new FeedbackPanel("feedback"));
		Form<EditFollowList> form = new Form<EditFollowList>("userListForm", new CompoundPropertyModel<EditFollowList>(this)) {
			@Override
			protected void onSubmit() {
				userList.getObject().setFollowers(usersList);
				userListRepo.updateList(userList.getObject());
				setResponsePage(new FollowList());
			}
		};
		IModel<List<TwattUser>> userModels =  new LoadableDetachableModel<List<TwattUser>>(){

			@Override
			protected List<TwattUser> load() {
				return userRepo.getAll();
			}
		};	
		this.usersList = userList.getObject().getFollowed();
		ListMultipleChoice<TwattUser> listUsernames = new ListMultipleChoice<TwattUser>("usersList", userModels);
		listUsernames.setChoiceRenderer(new ChoiceRenderer<TwattUser>("username", "id"));
		listUsernames.setMaxRows(userModels.getObject().size());
		form.add(listUsernames);
		form.add(new Button("submit", new ResourceModel("submit")));
		form.add(new Label("name", userList.getObject().getName()));
		add(form);
	}
}
