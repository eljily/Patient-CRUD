package net.sidibrahim.Hospital.security;

import lombok.AllArgsConstructor;
import net.sidibrahim.Hospital.security.service.AccountService;
import net.sidibrahim.Hospital.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
@Autowired
    private MvcRequestMatcher.Builder mvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    // @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
                );
    }
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity.formLogin((l)-> l.loginPage("/login").defaultSuccessUrl("/").permitAll());
       httpSecurity.authorizeHttpRequests((auth)->
               auth.requestMatchers(mvc.pattern("/webjars/**")).permitAll());
       /*httpSecurity.authorizeHttpRequests((auth)->
               auth.requestMatchers(mvc.pattern("/admin/**")).hasRole("ADMIN"));
       httpSecurity.authorizeHttpRequests((auth)->
               auth.requestMatchers(mvc.pattern("/user/**")).hasRole("USER"));*/
       httpSecurity.exceptionHandling((c)->c.accessDeniedPage("/notAuthorized"));
        httpSecurity.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .anyRequest().authenticated());
        httpSecurity.rememberMe(Customizer.withDefaults());
        httpSecurity.userDetailsService(userDetailsServiceImpl);
        return httpSecurity.build();
    }

}
