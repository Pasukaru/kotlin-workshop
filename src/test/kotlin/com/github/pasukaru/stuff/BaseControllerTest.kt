package com.github.pasukaru.stuff

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

abstract class BaseControllerTest : BaseSpringTest() {

    lateinit var mvc: MockMvc

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        TestExtensions.OBJECT_MAPPER = objectMapper
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
    }

    fun debug(resultActions: ResultActions): ResultActions {
        val result = resultActions.andReturn()
        println("URI:")
        println(result.request.requestURI)
        println("RESPONSE:")
        println(result.response.contentAsString)
        return resultActions
    }

    fun toJson(obj: Any): String {
        return objectMapper.writeValueAsString(obj)
    }

    protected final inline fun <reified T> parseResult(result: MvcResult): T {
        return objectMapper.readValue(result.response.contentAsString, T::class.java)
    }

}