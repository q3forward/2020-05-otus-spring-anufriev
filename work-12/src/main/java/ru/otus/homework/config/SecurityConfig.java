package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.homework.service.UserServiceImpl;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userServiceImpl;

    public SecurityConfig(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers( "/", "/login", "/listComment").permitAll()

                .antMatchers( "/addComment", "/editComment").authenticated()
                /*.antMatchers(HttpMethod.POST, "/api/comment").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/comment/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/comment/*").authenticated()*/

                .antMatchers( "/addBook", "/editBook").hasAuthority("ADMIN")
                /*.antMatchers(HttpMethod.POST, "/api/book").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/book/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/book/*").hasAuthority("ADMIN")*/

            .and().logout().logoutSuccessUrl("/")
            .and().formLogin();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userServiceImpl);
    }
}
