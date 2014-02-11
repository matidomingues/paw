package ar.edu.itba.paw.web.pages.userlist;

import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
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

public class AddFollowList extends SecuredPage{

	@SpringBean
	private UserRepo userRepo;
	
	@SpringBean
	UserListRepo userListRepo;
	
	private transient String name;
	private transient List<TwattUser> usersList;
	
	
	public AddFollowList(){
		add(new FeedbackPanel("feedback"));
		final IModel<TwattUser> viewerModel = getViewer();
		Form<AddFollowList> form = new Form<AddFollowList>("userListForm", new CompoundPropertyModel<AddFollowList>(this)) {
			@Override
			protected void onSubmit() {
				UserList list = new UserList(name, getViewer().getObject());
				list.addFollowers(usersList);
				userListRepo.saveList(list);
				setResponsePage(new FollowList());
			}
		};
		IModel<List<TwattUser>> userModels =  new LoadableDetachableModel<List<TwattUser>>(){

			@Override
			protected List<TwattUser> load() {
				return userRepo.getAll();
			}
		};			
		ListMultipleChoice<TwattUser> listUsernames = new ListMultipleChoice<TwattUser>("usersList", userModels);
		listUsernames.setChoiceRenderer(new ChoiceRenderer<TwattUser>("username", "id"));
		listUsernames.setMaxRows(userModels.getObject().size());
		form.add(new TextField<String>("name").setRequired(true));
		form.add(listUsernames);
		form.add(new Button("submit", new ResourceModel("submit")));
		add(form);
	}
}
