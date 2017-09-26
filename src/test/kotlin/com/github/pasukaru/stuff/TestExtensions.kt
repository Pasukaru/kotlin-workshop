package com.github.pasukaru.stuff

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import kotlin.jvm.internal.FunctionReference
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaMethod

class TestExtensions {
    companion object {
        lateinit var OBJECT_MAPPER: ObjectMapper
        val SUPPORTED_MAPPING_ANNOTATIONS = listOf(
            GetMapping::class.java,
            PutMapping::class.java,
            PostMapping::class.java,
            DeleteMapping::class.java,
            RequestMapping::class.java
        )
        val PARAMETER_NAME_REGEX = Regex("\\{([a-zA-Z0-9]+)}.*")
    }
}

fun KFunction<*>.post(
    body: Any?,
    pathVariables: Map<String, Any> = emptyMap()
): MockHttpServletRequestBuilder {
    return MockMvcRequestBuilders.post(this.getUrl(pathVariables))
        .accept(MediaType.APPLICATION_JSON)
        .also { p -> body?.let { p.jsonBody(body) } }
}

fun KFunction<*>.getRawUrl(): String {
    val owner = (this as FunctionReference).owner as KClass<*>

    val foundAnnotations = TestExtensions.SUPPORTED_MAPPING_ANNOTATIONS
        .map { this.javaMethod?.getAnnotationsByType(it)?.firstOrNull() }
        .filter { it != null }

    if (foundAnnotations.count() == 0) {
        throw IllegalAccessError("Method ${this} is not annotated with any of the following: @GetMapping, @PutMapping, @PostMapping, @DeleteMapping, @RequestMapping")
    }

    if (foundAnnotations.count() > 1) {
        throw IllegalAccessError("Method ${this} is annotated with more than one request mapping")
    }

    val annotation = foundAnnotations.first()
    val suffix = when (annotation) {
        is GetMapping -> annotation.value
        is PutMapping -> annotation.value
        is PostMapping -> annotation.value
        is DeleteMapping -> annotation.value
        is RequestMapping -> annotation.value
        else -> throw IllegalAccessError("Annotation not supported: $annotation")
    }.firstOrNull().orEmpty()

    val prefix = owner.findAnnotation<RequestMapping>()?.value?.get(0).orEmpty()
    val url = (prefix + suffix).let { if (!it.startsWith("/")) "/" + it else it }
    return url
}

fun KFunction<*>.getUrl(pathVariables: Map<String, Any> = emptyMap()): String {
    val rawUrl = this.getRawUrl()
    return pathVariables.toList().fold(rawUrl, { url, (key, value) ->
        url.replace("{$key}", value.toString(), ignoreCase = false)
            .also { current ->
                if (current == url) {
                    throw Exception("Parameter {$key} does not exist on url $rawUrl for method ${this}")
                }
                TestExtensions.PARAMETER_NAME_REGEX.find(current)?.let { match ->
                    throw Exception("Parameter ${match.value} was not replaced in url $rawUrl for method ${this}")
                }
            }
    })
}

fun ResultActions.debug(): ResultActions {
    this.andReturn().debug()
    return this
}

fun MvcResult.debug(): MvcResult {
    println("URI:")
    println(this.request.requestURI)
    println("RESPONSE:")
    println(this.response.contentAsString)
    return this
}

inline fun <reified T> ResultActions.andReturnResult(): T {
    val json = this.andReturn().response.contentAsString
    return TestExtensions.OBJECT_MAPPER.readValue(json, T::class.java)
}

fun MockHttpServletRequestBuilder.jsonBody(obj: Any): MockHttpServletRequestBuilder {
    return this.contentType("application/json")
        .content(TestExtensions.OBJECT_MAPPER.writeValueAsString(obj))
}

