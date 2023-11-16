package spharos.user.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Restful API를 사용하므로, csrf는 사용할 필요가 없다
                .csrf(CsrfConfigurer::disable)
                // cors는 사용할것이므로 CorsConfig만 @Configuration 등록하고 따로 설정해주지는 않음
                // 인증 절차에 대한 설정을 시작 : 필터를 적용시키지 않을 url과, 적용시킬 url을 구분
                .authorizeHttpRequests(

                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(org.springframework.web.cors.CorsUtils::isPreFlightRequest)
                                .permitAll()
                                // RESTful 하게 구분되는 경우 -> HttpMethod 까지 적어줘야한다
//                                .requestMatchers(
//                                        HttpMethod.GET, "/api/v1/franchise")
//                                .permitAll() // 모든 가맹점 정보 조회
                                // url이 특정지어지는 경우 -> HttpMethod를 적어줄 필요가 없다
                                .requestMatchers(
                                        "/**"                 // 토큰 재발급
                                )
                                .permitAll() // 위의 url은 모두 filter를 거치지 않음
//                                .anyRequest().authenticated() // 위의 url을 제외한 모든 url은 필터를 거쳐야함
                )
                // 폼 로그인 사용 안함
//                .formLogin(formLogin -> formLogin.disable())
                // 토큰 방식을 사용하므로, 서버에서 session을 관리하지 않음. 따라서 STATELESS로 설정
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ); // 세션을 생성하지 않음. JWT 인증이기 때문에 상태가 없는(stateless) 세션 정책을 사용
//                .authenticationProvider(authenticationProvider); // 커스텀 인증을 사용
        // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 전에 추가
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
