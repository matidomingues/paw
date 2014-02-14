package ar.edu.itba.paw.web.pages.user;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;

public class FindPage extends SecuredPage {

	private static final long serialVersionUID = 6865285172571616077L;

	@SpringBean
	private UserRepo userRepo;

	private transient String username;

	public FindPage(String query) {
		List<IModel<TwattUser>> userModels = new LinkedList<IModel<TwattUser>>();
		for (TwattUser user : userRepo.find(query)) {
			userModels.add(new EntityModel<TwattUser>(TwattUser.class, user));
		}
		this.loadList(userModels);
	}

	public FindPage(List<IModel<TwattUser>> userModels) {
		this.loadList(userModels);
	}

	public FindPage(IModel<List<TwattUser>> usersModel) {
		List<IModel<TwattUser>> userModels = new LinkedList<IModel<TwattUser>>();
		for (TwattUser user : usersModel.getObject()) {
			userModels.add(new EntityModel<TwattUser>(TwattUser.class, user));
		}
		this.loadList(userModels);
	}

	@SuppressWarnings("serial")
	private void loadList(final List<IModel<TwattUser>> userModels) {
		add(new FeedbackPanel("feedback"));
		add(new RefreshingView<TwattUser>("user") {
			@Override
			protected Iterator<IModel<TwattUser>> getItemModels() {
				return userModels.iterator();
			}

			@Override
			protected void populateItem(Item<TwattUser> item) {
				item.add(new BookmarkablePageLink<TwattUser>("userLink",
						ProfilePage.class, new PageParameters().add("user",
								item.getModelObject().getUsername()))
						.add(new Label("username", item.getModelObject()
								.getUsername())));
				item.add(new Label("name", item.getModelObject().getName()));
				item.add(new Label("surname", item.getModelObject()
						.getSurname()));
				item.add(new Label("date", item.getModelObject().getDate()
						.toString()));
			}
		});
		Form<Void> form = new Form<Void>("findForm");
		final AutoCompleteTextField<String> field = new AutoCompleteTextField<String>(
				"username", new Model<String>("")) {
			@Override
			protected Iterator<String> getChoices(String username) {
				return userRepo.findUsernames(username).iterator();
			}
		};
		form.add(field.setRequired(true));
		add(form);
		field.add(new AjaxFormSubmitBehavior(form, "onchange")
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target)
            {
            	System.out.println(field.getDefaultModel().getObject());
            	System.out.println(username);
                setResponsePage(new FindPage((String)field.getDefaultModel().getObject()));
            }

            @Override
            protected void onError(AjaxRequestTarget target)
            {
            }
        });

	}
}
