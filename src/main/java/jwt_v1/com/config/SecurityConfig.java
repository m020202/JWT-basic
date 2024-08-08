package jwt_v1.com.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsFilter corsFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // 세션을 사용하지 않겠다는 의미
                .sessionManagement(form -> form
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilter(corsFilter)
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/user/**").hasAnyRole("USER","MANAGER","ADMIN")
                        .requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
