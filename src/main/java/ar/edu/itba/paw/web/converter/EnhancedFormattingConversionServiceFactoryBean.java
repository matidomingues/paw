package ar.edu.itba.paw.web.converter;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;


public class EnhancedFormattingConversionServiceFactoryBean extends
		FormattingConversionServiceFactoryBean {

	private Converter<?, ?>[] converters;
	private UserFormatter userFormatter;

	@Autowired
	public EnhancedFormattingConversionServiceFactoryBean(
			Converter<?, ?>[] converters, UserFormatter userFormatter) {
		this.converters = converters;
		this.userFormatter = userFormatter;
	}

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		for (Converter<?, ?> c : converters) {
			registry.addConverter(c);
		}
		registry.addFormatterForFieldType(TwattUser.class, userFormatter);
	}
}
