package net.nlacombe.vault.vaultws.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import net.nlacombe.wsutils.filter.RequestLogFilter;
import net.nlacombe.wsutils.restexception.providers.RestExceptionFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfiguration
{
	@Bean
	public RestExceptionFeature restExceptionFeature()
	{
		return new RestExceptionFeature("net.nlacombe");
	}

	@Bean
	public RequestLogFilter requestLogFilter()
	{
		return new RequestLogFilter();
	}

	@Bean
	public JacksonJsonProvider jacksonJsonProvider()
	{
		ObjectMapper jsonConverter = new ObjectMapper();
		jsonConverter.registerModule(new JavaTimeModule());

		return new JacksonJsonProvider(jsonConverter);
	}
}
