package todoapp.security.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

/**
 * HttpServletRequest가 로그인 사용자 세션({@link UserSession}을 사용 할 수 있도록 지원하는 필터 구현체
 *
 * @author springrunner.kr@gmail.com
 */
public class UserSessionFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserSessionRepository sessionRepository;

    public UserSessionFilter(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserSession userSession = sessionRepository.get();
        UserSessionRequestWrapper requestWrapper = new UserSessionRequestWrapper(request, userSession);

        filterChain.doFilter(requestWrapper, response);
//        throw new UnsupportedOperationException("unimplemented feature for UserSessionFilter");
    }


    /**
     * 로그인 사용자 세션을 기반으로 인증 개체와 역할 확인 기능을 제공
     *
     * @author springrunner.kr@gmail.com
     */
    final class UserSessionRequestWrapper extends HttpServletRequestWrapper {

        final Optional<UserSession> userSession;

        protected UserSessionRequestWrapper(HttpServletRequest request, UserSession userSession) {
            super(request);
            this.userSession = Optional.ofNullable(userSession);
        }

        @Override
        public Principal getUserPrincipal() {
//            throw new UnsupportedOperationException("unimplemented feature for UserSessionRequestWrapper");
            return userSession.orElse(null);
        }

        @Override
        public boolean isUserInRole(String role) {
//            throw new UnsupportedOperationException("unimplemented feature for UserSessionRequestWrapper");
            return userSession.map(us -> us.hasRole(role)).orElse(false);
        }

    }

}
