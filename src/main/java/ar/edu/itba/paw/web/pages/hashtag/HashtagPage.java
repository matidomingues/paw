package ar.edu.itba.paw.web.pages.hashtag;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;

public class HashtagPage extends SecuredPage {
	
	private static final long serialVersionUID = 77803663888322154L;
	@SpringBean private static HashtagRepo hashtagRepo;
	
	public HashtagPage(PageParameters parameters) {
		this(new EntityModel<Hashtag>(Hashtag.class, hashtagRepo.getHashtag(parameters.get("hashtag").toString(""))));
	}

	public HashtagPage(IModel<Hashtag> hashtag) {
		// TODO Auto-generated constructor stub
	}

}
