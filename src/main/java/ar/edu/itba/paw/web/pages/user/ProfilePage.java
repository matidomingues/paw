package ar.edu.itba.paw.web.pages.user;


import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.web.pages.base.SecuredPage;

public class ProfilePage extends SecuredPage{

	public ProfilePage(final TwattUser user){
		
		if(user.getUsername().compareTo(super.getTwatterSession().getUsername()) != 0){
			user.addAccess();
		}
		
		add(new Label("username", user.getUsername()));
		add(new Label("description", user.getDescription()));
		add(new Label("name", user.getName()));
		add(new Label("surname", user.getSurname()));
		add(new Label("accesses", Long.toString(user.getAccess())));
		add(new Label("followers", Integer.toString(user.getFollowers().size())));
		add(new Label("followings", Integer.toString(user.getFollowings().size())));
		
	}
}
