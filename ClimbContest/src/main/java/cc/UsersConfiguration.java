package cc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

@Configuration
@EnableWebSecurity
public class UsersConfiguration extends WebSecurityConfigurerAdapter {

	private String projectId = "climbing-competitions";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/scoreCard/**", "/css/**", "/js/**").permitAll();
		http.authorizeRequests().antMatchers("/scoreAddition/**").hasRole("DUCK").and().formLogin();
		http.authorizeRequests().antMatchers("/scoreEdition/**").hasRole("DUCK").and().formLogin();
		http.authorizeRequests().antMatchers("/scoreImport/**").hasRole("DUCK").and().formLogin();
		http.authorizeRequests().antMatchers("/climberAddition/**").hasRole("DUCK").and().formLogin();
		http.csrf().disable();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Firestore firestore() {
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(projectId)
				.setTimestampsInSnapshotsEnabled(true).build();
		return firestoreOptions.getService();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		return executor;
	}

	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("pio").password(encoder().encode("1")).roles("DUCK").build());
		manager.createUser(
				User.withUsername("fell").password(encoder().encode("Araszi_jest+supeRRR!")).roles("DUCK").build());
		return manager;
	}
}
