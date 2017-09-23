package com.github.pasukaru

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

fun Int.plusOne(): Int {
    return this + 1
}

fun String.beCool(): String {
    return "$this is cool"
}

operator fun String.unaryPlus(): String {
    return this.toUpperCase()
}

class Ex3Extensions {

    @Test
    fun useExtension() {
        assertThat(10.plusOne()).isEqualTo(11)
        assertThat("everyone".beCool()).isEqualTo("everyone is cool")
    }

    @Test
    fun useCustomOperator() {
        assertThat(+"Hello World").isEqualTo("HELLO WORLD")
    }

}