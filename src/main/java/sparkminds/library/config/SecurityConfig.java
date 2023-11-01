package sparkminds.library.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sparkminds.library.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenFilter;

    @Bean
    SecurityFilterChain filerChain(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/auth/register").permitAll()
            .antMatchers("/auth/authenticate").permitAll()
            .antMatchers("/register/**").permitAll()
            .antMatchers("/auth/hello").authenticated();

        http.addFilterBefore(jwtAuthenFilter, UsernamePasswordAuthenticationFilter.class);
        return  http.build();
    }
}
