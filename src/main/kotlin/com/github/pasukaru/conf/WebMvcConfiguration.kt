package com.github.pasukaru.conf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*

@Configuration
class WebMvcConfiguration : WebMvcConfigurerAdapter() {

    @Bean
    fun localeResolver(): LocaleResolver {
        val resolver = AcceptHeaderLocaleResolver()
        resolver.supportedLocales = mutableListOf(Locale("en"))
        resolver.defaultLocale = Locale.ENGLISH

        return resolver
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val lci = LocaleChangeInterceptor()
        lci.paramName = "Accept-Language"
        return lci
    }

    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry!!.addInterceptor(localeChangeInterceptor())
    }
}