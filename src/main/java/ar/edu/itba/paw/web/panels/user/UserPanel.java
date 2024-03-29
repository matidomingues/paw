package ar.edu.itba.paw.web.panels.user;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

@SuppressWarnings("serial")
public class UserPanel extends Panel{
	
	public UserPanel(String id){
		super(id);		
		add(new TextField<String>("name").setRequired(true));
		add(new TextField<String>("surname").setRequired(true));
		add(new PasswordTextField("password").setRequired(true));
		add(new TextField<String>("description").setRequired(true));
	}
	
		
}
