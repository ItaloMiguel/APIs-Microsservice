package br.com.microsservice.auth.security.config;

import br.com.microsservice.auth.endpoint.property.JwtConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityCredentialsConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService detailsService;
    private final JwtConfiguration jwtConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource((request) -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, exception) -> response.sendError(SC_UNAUTHORIZED))
                .and()
                .addFilter(new UsernamePasswordAuthenticationFilter())
                .authorizeRequests()
                .antMatchers(jwtConfiguration.getLoginUrl()).permitAll()
                .antMatchers("v1/admin/course/**").hasRole("ADMIN")
                .anyRequest().authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
