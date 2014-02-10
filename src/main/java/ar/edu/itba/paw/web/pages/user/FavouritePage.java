package ar.edu.itba.paw.web.pages.user;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.twatt.TwattPanel;

public class FavouritePage extends SecuredPage {
	
	private static final long serialVersionUID = -1415659875078464720L;

	@SuppressWarnings("serial")
	public FavouritePage(final IModel<TwattUser> userModel) {
		add(new RefreshingView<Twatt>("favouritesList") {
			@Override
			protected Iterator<IModel<Twatt>> getItemModels() {
				List<IModel<Twatt>> twattModels = new LinkedList<IModel<Twatt>>();
				for (Twatt twatt : userModel.getObject().getFavourites()) {
					twattModels.add(new EntityModel<Twatt>(Twatt.class, twatt));
				}
				return twattModels.iterator();
			}

			@Override
			protected void populateItem(Item<Twatt> item) {
				item.add(new TwattPanel("favourite", item.getModel(), getViewer()));					
			}
		});
	}
}
