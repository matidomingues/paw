package ar.edu.itba.paw.web.pages.user;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.twatt.TwattListPanel;

public class FavouritePage extends SecuredPage {
	
	private static final long serialVersionUID = -1415659875078464720L;

	public FavouritePage(final IModel<TwattUser> userModel) {
		add(new TwattListPanel("twatts", TwattListPanel.convertList(userModel.getObject().getFavourites()),
				userModel, new StringResourceModel("noFavouritesMessage", this, null)));
	}
}
