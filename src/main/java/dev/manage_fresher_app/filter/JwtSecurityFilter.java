package dev.manage_fresher_app.filter;

import dev.manage_fresher_app.entities.Account;
import dev.manage_fresher_app.exceptions.ResourceNotFoundException;
import dev.manage_fresher_app.repositories.AccountRepository;
import dev.manage_fresher_app.service.JwtService;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AccountRepository accountRepository;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getServletPath();
        if(requestPath.startsWith("/api/account")){
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = this.getJwtFromHeader(request);
        final String id;

        if(ObjectUtils.isEmpty(jwt)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            id = this.jwtService.extractID(jwt);
            Account account = accountRepository.findById(Long.parseLong(id)).orElseThrow(() -> new ResourceNotFoundException("Id không tồn tại"));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(account,
                    null,
                    account.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        }
        catch (MalformedJwtException e){
            logger.error("Jwt exception", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String getJwtFromHeader(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return null;
        }

        String jwt = authHeader.substring(7);
        return jwt.equals("null") ? null : jwt;
    }
}