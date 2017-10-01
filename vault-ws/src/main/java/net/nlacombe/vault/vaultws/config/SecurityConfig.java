package net.nlacombe.vault.vaultws.config;

import net.nlacombe.authlib.spring.AuthTokenAuthenticationFilter;
import net.nlacombe.authlib.spring.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
				.csrf().disable()
				.cors().configurationSource(corsConfigurationSource()).and()
				.addFilterBefore(authTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint()).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.mvcMatchers("/health").permitAll()
				.anyRequest().authenticated();
	}

	@Bean
	public AuthTokenAuthenticationFilter authTokenAuthenticationFilter()
	{
		return new AuthTokenAuthenticationFilter();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource()
	{
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig = corsConfig.applyPermitDefaultValues();
		corsConfig.addAllowedMethod(HttpMethod.PUT);
		corsConfig.addAllowedMethod(HttpMethod.DELETE);

		UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();
		corsConfigSource.registerCorsConfiguration("/**", corsConfig);

		return corsConfigSource;
	}
}
