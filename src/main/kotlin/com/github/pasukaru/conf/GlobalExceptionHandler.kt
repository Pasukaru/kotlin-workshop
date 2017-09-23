package com.github.pasukaru.conf

import com.github.pasukaru.dto.controller.out.ApiError
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.reflect.UndeclaredThrowableException
import javax.servlet.http.HttpServletResponse

@Suppress("unused")
@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    fun accessDenied(ex: org.springframework.security.access.AccessDeniedException): ApiError {
        logger.info(ex.message, ex)
        return ApiError(ex.message, "access_denied")
    }

    @ExceptionHandler(UndeclaredThrowableException::class)
    @ResponseBody
    fun undeclaredThrowableException(ex: UndeclaredThrowableException, response: HttpServletResponse): ApiError {
        logger.info(ex.message, ex)
        response.status = 500
        return ApiError(ex.message)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleException(ex: Exception): ApiError {
        logger.info(ex.message, ex)
        return ApiError(ex.message)
    }

    override fun handleBindException(ex: BindException?, headers: HttpHeaders?, status: HttpStatus?, request: WebRequest?): ResponseEntity<Any> {
        logger.info("An Invalid BIND EXCEPTION occurred: ${ex?.message}", ex)
        val apiError = ApiError("Error while processing request")
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.UNPROCESSABLE_ENTITY, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest): ResponseEntity<Any> {
        logger.warn(ex.message, ex)
        return ResponseEntity.badRequest().body<Any>(ApiError(ex.message))
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest): ResponseEntity<Any> {

        logger.info(ex.message, ex)

        if (ex.bindingResult.fieldError != null) {
            return handleExceptionInternal(ex, ApiError(ex.bindingResult.fieldError.defaultMessage, "invalid_body"), headers, HttpStatus.UNPROCESSABLE_ENTITY, request)
        }

        if (ex.bindingResult.globalError != null) {
            return handleExceptionInternal(ex, ApiError(ex.bindingResult.globalError.defaultMessage, "invalid_body"), headers, HttpStatus.UNPROCESSABLE_ENTITY, request)
        }

        return handleExceptionInternal(ex, ApiError(ex.message), headers, HttpStatus.UNPROCESSABLE_ENTITY, request)
    }
}