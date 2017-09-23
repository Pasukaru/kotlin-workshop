package com.github.pasukaru.auth

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

//TODO: Replace Any with actual principal object
class TokenAuthentication : Authentication {
    var _grantedAuthorities = mutableListOf<GrantedAuthority>()
    var _credentials: Any = Object()
    var _details: Any = Object()
    var _principal: Any = Any()
    var _isAuthenticated: Boolean = false

    constructor(token: String) {
        this._credentials = token
    }

    constructor(principal: Any, grantedAuthorities: MutableList<GrantedAuthority>) {
        this._principal = principal
        this._grantedAuthorities = grantedAuthorities
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this._grantedAuthorities
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        _isAuthenticated = isAuthenticated
    }

    override fun getName(): String {
        return ""
    }

    override fun getCredentials(): Any {
        return _credentials
    }

    override fun getPrincipal(): Any {
        return _principal
    }

    override fun isAuthenticated(): Boolean {
        return _isAuthenticated
    }

    override fun getDetails(): Any {
        return _details
    }
}