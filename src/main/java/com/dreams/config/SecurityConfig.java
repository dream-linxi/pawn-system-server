package com.dreams.config;

import com.dreams.security.qqLogin.QQAuthenticationFilter;
import com.dreams.security.qqLogin.QQAuthenticationProvider;
import com.dreams.security.qqLogin.QQSuccessHandler;
import com.dreams.sys.service.UserService;
import com.dreams.security.PhoneAuthenticationFilter;
import com.dreams.security.PhoneAuthenticationProvider;
import com.dreams.security.PhoneCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dreams.security.ImageCodeFilter;

/**
 * Security 配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	
	@Autowired
	private AuthenticationFailureHandler failureHandler;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ImageCodeFilter imageCodeFilter;

	@Autowired
	private PhoneCodeFilter phoneCodeFilter;

	@Autowired
	private QQSuccessHandler qqSuccessHandler;
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public CorsConfigurationSource CorsConfigurationSource() {
		CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("https://graph.qq.com/oauth2.0/authorize");	//同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
		corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
		corsConfiguration.addAllowedMethod("*");	//允许的请求方法，PSOT、GET等
		((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
		return source;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		PhoneAuthenticationFilter phoneAuthenticationFilter = new PhoneAuthenticationFilter();
		phoneAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
		phoneAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
		phoneAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		PhoneAuthenticationProvider phoneAuthenticationProvider = new PhoneAuthenticationProvider(userService);

		QQAuthenticationFilter qqAuthenticationFilter = new QQAuthenticationFilter();
		qqAuthenticationFilter.setAuthenticationSuccessHandler(qqSuccessHandler);
		qqAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
		qqAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		QQAuthenticationProvider qqAuthenticationProvider = new QQAuthenticationProvider(userService);



		http
				.cors().configurationSource(CorsConfigurationSource()).and()
				.httpBasic().disable()
				.csrf().disable()
				//.cors().configurationSource(corsConfigurationSource()).and()
				.headers().frameOptions().disable()
				.and()
				.authenticationProvider(phoneAuthenticationProvider)
				.authenticationProvider(qqAuthenticationProvider)
				.addFilterAfter(phoneAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(qqAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(imageCodeFilter,UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(phoneCodeFilter,UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			// 授权页面和行为
			.antMatchers("/login.html","/sendCode","/phoneLogin","/login","/common/**","/css/**",
					"/images/**","/js/**","/layui/**","/login/**","/getImageCode","/syspages/**").permitAll()
			// 拦截任意请求
			.anyRequest().authenticated()
			.and()
			.formLogin()
			// 登录页面
			.loginPage("/login.html")
			// 拦截请求
			.loginProcessingUrl("/login")
			.successHandler(successHandler)
			.failureHandler(failureHandler)
		;


	}
	
//	public static void main(String[] args) {
//		// $2a$10$9wU4ntDVTQTG5lfyATP7/emRBAmgmuj6/Y7YgRhBM8sUIlFQVJBte
//		String s = "123321";
//		PasswordEncoder pe = new BCryptPasswordEncoder();
//		System.out.println(pe.encode(s));
//	}
}
