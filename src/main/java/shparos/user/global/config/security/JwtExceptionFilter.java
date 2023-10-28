package shparos.user.global.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter  {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityCustomException ex) {
            log.error("JwtExceptionFilter={}",ex.getJwtExceptionCode());

            if(JwtExceptionCode.UNKNOWN_ERROR.equals(ex)) {
                setResponse(response, JwtExceptionCode.UNKNOWN_ERROR);
            }
            //잘못된 타입의 토큰인 경우
            else if(JwtExceptionCode.WRONG_TYPE_TOKEN.equals(ex.getJwtExceptionCode())) {
                setResponse(response, JwtExceptionCode.WRONG_TYPE_TOKEN);
            }
            //토큰 만료된 경우
            else if(JwtExceptionCode.EXPIRED_TOKEN.equals(ex.getJwtExceptionCode())) {
                setResponse(response, JwtExceptionCode.EXPIRED_TOKEN);
            }
            //지원되지 않는 토큰인 경우
            else if(JwtExceptionCode.UNSUPPORTED_TOKEN.equals(ex.getJwtExceptionCode())) {
                setResponse(response, JwtExceptionCode.UNSUPPORTED_TOKEN);
            }
            else {
                setResponse(response, JwtExceptionCode.ACCESS_DENIED);
            }
        }
    }

    private void setResponse(HttpServletResponse response, JwtExceptionCode errorMessage) throws RuntimeException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorMessage.getHttpStatus().value());
        response.getWriter().print(errorMessage.getDetail());
    }

}
