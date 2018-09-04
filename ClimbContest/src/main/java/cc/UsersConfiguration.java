package cc;

import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import cc.repository.ClimbersRepository;

@Configuration
@EnableWebSecurity
public class UsersConfiguration extends WebSecurityConfigurerAdapter {

	private String projectId = "climbing-competitions";

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("pio").password(encoder().encode("1")).roles("DUCK").build());
		manager.createUser(
				User.withUsername("fell").password(encoder().encode("Araszi_jest+supeRRR!")).roles("DUCK").build());
		return manager;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() //
				.antMatchers("/public/**", "/css/**", "/js/**").permitAll() //
				.antMatchers("/secret/**").hasRole("DUCK").and().formLogin();
	}

	// cc app beans:

	@Bean
	ClimbersRepository climbersRepository() throws InterruptedException, ExecutionException {
		return new ClimbersRepository(projectId);
	}
}
