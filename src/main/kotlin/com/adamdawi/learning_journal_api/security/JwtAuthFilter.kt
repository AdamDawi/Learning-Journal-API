package com.adamdawi.learning_journal_api.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

// for every single request
@Component
class JwtAuthFilter(
    private val jwtService: JwtService
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Bearer <token>
        val authHeader = request.getHeader("Authorization")
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            if(jwtService.validateAccessToken(authHeader)){
                val userId = jwtService.getUserIdFromToken(authHeader)
                val auth = UsernamePasswordAuthenticationToken(userId, null, emptyList())
                // to retrieve auth across codebase
                SecurityContextHolder.getContext().authentication = auth
            }
        }
        filterChain.doFilter(request, response)
    }
}