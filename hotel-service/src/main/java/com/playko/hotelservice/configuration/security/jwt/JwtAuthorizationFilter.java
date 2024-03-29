package com.playko.hotelservice.configuration.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private List<String> excludedPrefixes = Arrays.asList("/swagger-ui/**", "/v3/api-docs/**");
    private AntPathMatcher pathMatcher = new AntPathMatcher();


    private boolean isEndpointMatch(String allowedEndpoint, String actualEndpoint) {
        String regex = allowedEndpoint
                .replaceAll("\\{id\\}", "[^/]+")
                .replaceAll("\\{hotelId\\}", "[^/]+"); // Reemplazar {hotelId} con el valor actual
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(actualEndpoint).matches();
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String endpoint = request.getRequestURI();
        Map<String, List<String>> rolesEndpointsMap = new HashMap<>();
        // Agrega más roles y sus respectivos endpoints según sea necesario
        rolesEndpointsMap.put("ROLE_ADMIN", Arrays.asList("/admin/v1/save-hotel", "/admin/v1/generate-excel", "/admin/v1/generate-pdf"));
        rolesEndpointsMap.put("ROLE_CLIENT", Arrays.asList("/hotel/v1/client/list-hotels", "/hotel/v1/client/list-rooms/{hotelId}", "/hotel/v1/client/saveReservation"));


        // Hacer la excepcion para el token
        try {
            String token = getToken(request);
            if (token == null || !JwtUtils.validateJwtToken(token)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            List<String> roles = JwtUtils.getRoles(token);
            if (!roles.stream().anyMatch(rolesEndpointsMap::containsKey)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            List<String> allowedEndpoints = new ArrayList<>();
            for (String role : roles) {
                List<String> roleEndpoints = rolesEndpointsMap.get(role);
                if (roleEndpoints != null) {
                    allowedEndpoints.addAll(roleEndpoints);
                }
            }

            boolean isEndpointAllowed = false;
            for (String allowedEndpoint : allowedEndpoints) {
                if (isEndpointMatch(allowedEndpoint, endpoint)) {
                    isEndpointAllowed = true;
                    break;
                }
            }

            if (!isEndpointAllowed) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRoute = request.getServletPath();
        for (String prefix : excludedPrefixes) {
            if (pathMatcher.match(prefix, currentRoute)) {
                return true;
            }
        }
        return false;
    }

    private String getToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

}
