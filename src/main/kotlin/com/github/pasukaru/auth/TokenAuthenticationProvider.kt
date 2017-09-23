package com.github.pasukaru.auth

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
@Transactional
class TokenAuthenticationProvider : AuthenticationProvider {

    /*
    @Autowired
    lateinit var sessionRepository: SessionRepository
    */

    override fun authenticate(authentication: Authentication): Authentication {
        /*
        authentication.credentials?.let {
            val tokenStr = authentication.credentials as? String
            tokenStr ?: return authentication

            if ("undefined" != tokenStr && "null" != tokenStr) {
                val session = sessionRepository.findOne(UUID.fromString(tokenStr))
                session?.let {
                    if (!session.user.active) return authentication

                    val grantedAuths = ArrayList<GrantedAuthority>()
                    session.user.role.rights.forEach { r -> grantedAuths.add(SimpleGrantedAuthority(r.name.toString())) }

                    val tokenAuth = authentication as TokenAuthentication
                    tokenAuth._principal = session
                    tokenAuth._grantedAuthorities = grantedAuths

                    return tokenAuth
                }
            }
        }
        */

        return authentication
    }

    override fun supports(authentication: Class<*>): Boolean {
        return true
    }
}