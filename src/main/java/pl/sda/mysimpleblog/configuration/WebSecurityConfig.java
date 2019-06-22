package pl.sda.mysimpleblog.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // konfiguracja zabezpieczeń dla protokołu http
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/addpost").hasAnyRole("USER") // wymaga upr USER
                .antMatchers("/update/*").hasAnyRole("USER")
                .anyRequest().permitAll()                                    // pozostałe bez upr
        .and().formLogin()
        .and().httpBasic();
    }
}
