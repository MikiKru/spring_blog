package pl.sda.mysimpleblog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // konfiguracja zabezpieczeń dla protokołu http
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/addpost").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") // wymaga upr USER
                .antMatchers("/update/*").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/delete/*").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().permitAll()                                    // pozostałe bez upr

                .and()
                .csrf().disable()
                .formLogin().loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login-process")
                    .defaultSuccessUrl("/")

                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }
    @Autowired
    DataSource dataSource;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("SELECT u.email, u.password, u.activity FROM user u WHERE u.email = ?")
                .authoritiesByUsernameQuery("SELECT u.email, r.role_name FROM " +
                                                "user u  JOIN user_role ur ON (u.id = ur.user_id) " +
                                                        "JOIN role r ON (r.id = ur.role_id) " +
                                                "WHERE u.email = ?")
                .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
    }


}
