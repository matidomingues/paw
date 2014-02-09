package ar.edu.itba.paw.web.pages.user;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import ar.edu.itba.paw.web.pages.base.SecuredPage;

public class ReportPage extends SecuredPage{

	public ReportPage(PageParameters parameters){
		this(parameters.get("days"), parameters.get("startDate"), parameters.get("endDate"));
	}
	
	public ReportPage(StringValue days, StringValue startDate, StringValue endDate){
		
	}
	
}
