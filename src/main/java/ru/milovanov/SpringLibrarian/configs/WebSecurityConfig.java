package ru.milovanov.SpringLibrarian.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.milovanov.SpringLibrarian.dao.impl.UserDaoImpl;

import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //@Autowired
    //private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /*auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}2212")
                .authorities("ROLE_ADMIN")
                .and()
                .withUser("user")
                .password("{noop}1535")
                .authorities("ROLE_ADMIN");

        */
        String encoded=new BCryptPasswordEncoder().encode("2212");
        System.out.println(encoded);

        auth.jdbcAuthentication()
                .dataSource(jdbcTemplate.getDataSource())
                .usersByUsernameQuery(
                        "select username, password, enabled from users " +
                                "where username=?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities " +
                                "where username=?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest()
                .hasAuthority("ROLE_ADMIN")
        .and()
        .httpBasic();
    }



}
