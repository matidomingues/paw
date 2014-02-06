package ar.edu.itba.paw.web;


import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.utils.HibernateRequestCycleListener;
import ar.edu.itba.paw.web.pages.home.HomePage;
import ar.edu.itba.paw.web.pages.login.LoginPage;

@Component
public class TwatterApp extends WebApplication {


	public static final ResourceReference DEFAULT_IMAGE = new PackageResourceReference(TwatterApp.class, "resources/default_user_icon.png");
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getRequestCycleListeners().add(new HibernateRequestCycleListener(sessionFactory));
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new TwatterSession(request);
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
		//Here's where the converters are registered
		return converterLocator;
	}
	
}