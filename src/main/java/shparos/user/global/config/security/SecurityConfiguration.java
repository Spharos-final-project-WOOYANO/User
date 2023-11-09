package shparos.user.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(org.springframework.web.cors.CorsUtils::isPreFlightRequest)
                                .permitAll()
                                .requestMatchers("/**")
                                .permitAll() // 모든 사용자에게 접근을 허용합니다.
                                .anyRequest() // 그 외의 모든 요청에 대해
                                .authenticated() // 인증된 사용자만 접근을 허용합니다.
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ); // 세션을 생성하지 않음. JWT 인증이기 때문에 상태가 없는(stateless) 세션 정책을 사용합니다.

        return http.build();

    }

}
