package ar.edu.itba.paw.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Component;

public class EnhancedFormattingConversionServiceFactoryBean extends
		FormattingConversionServiceFactoryBean {

	private Converter<?, ?>[] converters;

	@Autowired
	public EnhancedFormattingConversionServiceFactoryBean(
			final Converter<?, ?>[] converters) {
		System.out.println("entro");
		this.converters = converters;
	}

	@Override
	protected void installFormatters(final FormatterRegistry registry) {
		super.installFormatters(registry);
		System.out.println("paso");
		for (final Converter<?, ?> c : this.converters) {
			System.out.println(c);
			registry.addConverter(c);
		}
	}
}