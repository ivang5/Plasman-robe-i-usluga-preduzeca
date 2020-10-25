package com.example.poslovnaInformatikaFTN.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

import com.example.poslovnaInformatikaFTN.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(
			AuthenticationManagerBuilder authenticationManagerBuilder)
			throws Exception {
		
		authenticationManagerBuilder
				.userDetailsService(this.userDetailsService).passwordEncoder(
						passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean()
			throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter
				.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
			//.antMatchers("/index.html", "/api/login", "/api/register", "/api/**").permitAll()
			.antMatchers("/**").permitAll()
			.antMatchers(HttpMethod.GET, "/api/**", "/").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
			.antMatchers(HttpMethod.POST, "/api/**", "/").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/**", "/").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/**", "/").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/preduzece/**", "/api/zaposleni/**")
			.hasAuthority("ROLE_ADMIN") //only administrator can edit data
			.antMatchers(HttpMethod.DELETE, "/api/preduzece/**", "/api/zaposleni/**")
			.hasAuthority("ROLE_ADMIN")
			.anyRequest().authenticated().and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			/*.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.authorizeRequests()*/
				//.antMatchers("/index.html", "/api/login", "/api/register", "/api/**").permitAll()
				/*.antMatchers("/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/**", "/").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
				.antMatchers(HttpMethod.POST, "/api/**", "/").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/**")
				.hasAuthority("ROLE_ADMIN") //only administrator can edit data
				.antMatchers(HttpMethod.DELETE, "/api/**")
				.hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated();*/
				 
		
		// Custom JWT based authentication
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(),
				UsernamePasswordAuthenticationFilter.class);
	}
	

}
