package com.github.pasukaru.auth

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

@Component
class AuthFilter : Filter {

    override fun init(filterConfig: FilterConfig?) {
    }

    override fun destroy() {
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        var token = httpRequest.getHeader("x-auth-token")
        if (token == null || token.isBlank()) {
            token = httpRequest.getParameter("x-auth-token")
        }

        if (token != null && !token.isBlank()) {
            SecurityContextHolder.getContext().authentication = TokenAuthentication(token)
        } else {
            SecurityContextHolder.clearContext()
        }

        chain?.doFilter(request, response)
    }
}