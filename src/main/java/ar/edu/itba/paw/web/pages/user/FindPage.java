package ar.edu.itba.paw.web.pages.user;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;

public class FindPage extends SecuredPage{
	
	@SpringBean
	private UserRepo userRepo;
	
	private transient String username;
	
	public FindPage(final String query){
		
		
		final IModel<List<TwattUser>> userModel = new LoadableDetachableModel<List<TwattUser>>() {
			@Override
			protected List<TwattUser> load() {
				return userRepo.find(query);
			}
		};
		
		ListView<TwattUser> listview = new PropertyListView<TwattUser>("user", userModel) {
			@Override
			protected void populateItem(ListItem<TwattUser> item) {
				item.add(new Link<TwattUser>("userLink", item.getModel()) {
					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(getModelObject()));
					}
				}.add(new Label("username")));
				item.add(new Label("name"));
				item.add(new Label("surname"));
				item.add(new Label("date"));
			}
			
		};
		add(new FeedbackPanel("feedback"));
		add(listview);
		add(searchForm());
	}
	
	private Form<FindPage> searchForm(){
		Form<FindPage> form = new Form<FindPage>("findForm", new CompoundPropertyModel<FindPage>(this)) {
			@Override
			protected void onSubmit() {
				setResponsePage(new FindPage(username));
			}
		};
		form.add(new TextField<String>("username").setRequired(true));
		form.add(new Button("search", new ResourceModel("search")));
		return form;
	}
}
