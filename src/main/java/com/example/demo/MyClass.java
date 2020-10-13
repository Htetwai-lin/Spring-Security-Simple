package com.example.demo;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@EnableWebMvc
@EnableWebSecurity
public class MyClass extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	@Autowired
	private BCryptPasswordEncoder encode;
	
	@Autowired
	private DataSource dataSource;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/member/**").hasAnyRole("ADMIN","MEMBER")
			.antMatchers("/admin/*").hasRole("ADMIN")
		.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/home")
		.and()
			.logout()
			.logoutUrl("/logout")
		.and()
			.rememberMe()
			.key("mySectetKey");
	 
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	/*
	 * using security by jdbc.
	 */
		auth.jdbcAuthentication()
		.passwordEncoder(encode)
		.dataSource(dataSource)
		.usersByUsernameQuery("select login,password,enable from users where login = ?")
		.authoritiesByUsernameQuery("select login,role from users where login =?");
	/*
	 * Using security by in memory.
		
		auth.inMemoryAuthentication()
		.passwordEncoder(encode)
		.withUser("member")
		.password(encode.encode("member"))
		.roles("MEMBER");
		 */
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry view) {
		view.addViewController("/").setViewName("index");
		view.addViewController("/member/home").setViewName("member/home");
		view.addViewController("/login").setViewName("login");
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		BCryptPasswordEncoder cry = new BCryptPasswordEncoder();
		System.out.println(cry.encode("member"));
		System.out.println(cry.encode("admin"));
	}
}
