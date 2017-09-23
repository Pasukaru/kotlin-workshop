package com.github.pasukaru

import com.github.pasukaru.controller.PingController
import com.github.pasukaru.controller.PingController.PingResponse
import com.github.pasukaru.controller.PingController.PingResponseWithParam
import com.github.pasukaru.stuff.BaseControllerTest
import com.github.pasukaru.stuff.andReturnResult
import com.github.pasukaru.stuff.debug
import com.github.pasukaru.stuff.post
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

class Ex5ExtensionsForSpring : BaseControllerTest() {

    @Test
    fun withoutExtensions() {
        val body = PingResponse("Hello World")
        val uuid = UUID.randomUUID()

        val result = mvc.perform(
            MockMvcRequestBuilders.post("/ping/$uuid")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(body))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val dto = parseResult<PingResponseWithParam>(result)
        assertThat(dto.message).isEqualTo("Hello World")
        assertThat(dto.paramValue).isEqualTo(uuid)
    }

    @Test
    fun withoutExtensionsDebug() {
        val body = PingResponse("Hello World")
        val uuid = UUID.randomUUID()

        val resultActions = mvc.perform(
            MockMvcRequestBuilders.post("/ping/$uuid")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(body))
        )

        val result = debug(resultActions)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val dto = parseResult<PingResponseWithParam>(result)
        assertThat(dto.message).isEqualTo("Hello World")
        assertThat(dto.paramValue).isEqualTo(uuid)
    }

    @Test
    fun withExtensions() {
        val body = PingResponse("Hello World")
        val uuid = UUID.randomUUID()
        val pathVariables = mapOf(Pair("uuid", uuid))

        val result = mvc.perform(PingController::pingWithParam.post(body, pathVariables))
            .debug()
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturnResult<PingResponseWithParam>()

        assertThat(result.message).isEqualTo("Hello World")
        assertThat(result.paramValue).isEqualTo(uuid)
    }

}
