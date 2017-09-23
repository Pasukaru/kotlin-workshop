package com.github.pasukaru

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Ex8InlineFunction {

    var count = 0

    @BeforeEach
    fun setUp() {
        count = 0
    }

    private fun inlineFunctionWithReturn() {
        listOf(1, 2, 3).forEach { number ->
            count = number
            if (count == 2) {
                return
            }
        }
        count = 4
    }

    @Test
    fun returnReturnsBaseFunction() {
        inlineFunctionWithReturn()
        //Assertions.assertThat(count).isEqualTo(???)
    }

}