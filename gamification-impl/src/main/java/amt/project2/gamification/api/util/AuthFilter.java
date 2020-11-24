package amt.project2.gamification.api.util;


import amt.project2.gamification.entities.ApplicationEntity;
import amt.project2.gamification.repositories.ApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    @Autowired
    ApplicationRepository applicationRepository;


    Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("init filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if (isPublicResource(req.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        ApplicationEntity targetApp = applicationRepository.findByApiKey(req.getHeader("X-API-KEY"))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (targetApp == null) {
            ((HttpServletResponse) servletResponse).sendRedirect("/");
        }

        servletRequest.setAttribute("app",targetApp);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    boolean isPublicResource(String URI) {
        if (URI.equals("/")) {
            return true;
        }
        if (URI.startsWith("/v3")) {
            return true;
        }
        if (URI.startsWith("/swagger")) {
            return true;
        }
        if (URI.startsWith("/registrations")) {
            return true;
        }
        if (URI.startsWith("/auth")) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {
        LOG.info("destroy filter");
    }
}
