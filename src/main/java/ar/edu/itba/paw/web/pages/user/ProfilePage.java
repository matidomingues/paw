package ar.edu.itba.paw.web.pages.user;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.web.pages.base.SecuredPage;

public class ProfilePage extends SecuredPage{

	public ProfilePage(final TwattUser user){
		add(new Label("username", new PropertyModel<TwattUser>(user, "username")));
		add(new Label("description", new PropertyModel<TwattUser>(user, "description")));
	}
}
