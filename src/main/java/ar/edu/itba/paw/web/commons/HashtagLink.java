package ar.edu.itba.paw.web.commons;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.web.pages.hashtag.HashtagPage;

public class HashtagLink extends Link<Hashtag> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 385893510389315239L;

	public HashtagLink(String id, IModel<Hashtag> model) {
		super(id, model);
	}

	@Override
	public void onClick() {
		setResponsePage(new HashtagPage(getModelObject()));		
	}

}
