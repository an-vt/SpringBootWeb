package com.vuan;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.vuan.utils.PasswordGenerator;

@SpringBootApplication
public class MySpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringBootApplication.class, args);
	}

	@Autowired
	UserDetailsService userDetailsService; 

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
		 return bCryptPasswordEncoder;
	}

	@Order(1)
	@Configuration
	public class AdminSecurity extends WebSecurityConfigurerAdapter {
	
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// phan quyen trong trang admin
			System.out.println("authitencation called");
			http.csrf().disable().antMatcher("/admin/**").authorizeRequests().antMatchers("/admin/**").hasAnyRole("ADMIN").anyRequest()
					.permitAll()
					// cau hinh giao dien
					.and().formLogin().loginPage("/login-admin").loginProcessingUrl("/admin/login-adminUrl")
					.defaultSuccessUrl("/admin/index" ,true).failureUrl("/login-admin?e=error").and().logout()
					.logoutUrl("/admin/logout").logoutSuccessUrl("/login-admin").permitAll()
					// exeption
					.and().exceptionHandling().accessDeniedPage("/login-admin?e=deny")
					.and().httpBasic();

		}
		
//		@Override
//		public void configure(WebSecurity web) throws Exception {
//			web.ignoring().antMatchers("/static_admin/build/** ,/static_admin/css/** ,/static_admin/images/** ,/static_admin/js/** ,"
//					+ "/static_admin/vendors/**");
//		}

	}

	@Order(2)
	@Configuration 
	public class ClientSecurity extends WebSecurityConfigurerAdapter {
		
		protected void configure(HttpSecurity http) throws Exception {
			System.out.println("authitencation called");
			// phan quyen trong trang client
			http.csrf().disable().authorizeRequests().antMatchers("/member/**").hasAnyRole("MEMBER").anyRequest()
					.permitAll()
					// cau hinh giao dien
					.and().formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?e=error").permitAll().and().logout()
					.logoutUrl("/logout").permitAll()
					// exeption
					.and().exceptionHandling().accessDeniedPage("/login?e=deny").
					and().httpBasic();
		}

	}
	
	
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("*"));
//		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
}
