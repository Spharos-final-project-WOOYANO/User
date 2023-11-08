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

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(org.springframework.web.cors.CorsUtils::isPreFlightRequest)
                                .permitAll()
                                .requestMatchers(
                                        "/error",
                                        "/api/v1/users/join",
                                        "/api/v1/users/login",
                                        "/api/v1/users/email/check",
                                        "/api/v1/users/nickname/check",
                                        "/api/v1/users/email/find",
                                        "/api/v1/users/password",
                                        "/api/v1/users/email/exist/check",


                                        // swagger
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/swagger-resources/**",
                                        "/api-docs/**") // "/api/v1/auth/**" 패턴에 일치하는 요청에 대해
                                .permitAll() // 모든 사용자에게 접근을 허용합니다.
                                .anyRequest() // 그 외의 모든 요청에 대해
                                .authenticated() // 인증된 사용자만 접근을 허용합니다.
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) // 세션을 생성하지 않음. JWT 인증이기 때문에 상태가 없는(stateless) 세션 정책을 사용합니다.

                // 커스터마이징한 인증 제공자를 설정
                .authenticationProvider(authenticationProvider)
                // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 전에 추가합니다.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // jwt 에러처리를 위한 필터등록
                .addFilterBefore(jwtExceptionFilter,JwtAuthenticationFilter.class);


        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return  request -> {
            var cors = new org.springframework.web.cors.CorsConfiguration();
            cors.setAllowedOriginPatterns(List.of("*"));
            cors.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        };
    }

}
