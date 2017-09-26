@file:Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER", "RedundantExplicitType", "ALWAYS_NULL")

package com.github.pasukaru

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.Test

class Ex1KotlinBasics {

    // Variables

    data class Item(
        var name: String?
    )

    @Test
    fun kotlinVariables() {
        // Variables
        var variable: String = "Hello World"
        variable = "something else"
        assertThat(variable).isEqualTo("something else")

        // Constants
        val constant = "Hello World"
        // constant = "something else" // -> compilation error
        assertThat(constant).isEqualTo("Hello World")

        // Nullable
        val nullable: String? = null
        assertThat(nullable).isNull()
        // variable = null // -> compilation error
    }

    @Test
    fun propertiesOfClasses() {
        // No 'new' keyword required
        val item = Item("Hello World")

        // No need to call 'getter' methods - this will be done by kotlin automatically
        assertThat(item.name).isEqualTo("Hello World")

        // Also, no need to call a setter method:
        item.name = "something else"
        assertThat(item.name).isEqualTo("something else")
    }

    // Lateinit var

    class LateinitItem {
        lateinit var name: String
    }

    @Test
    fun lateinitExample() {
        val item = LateinitItem()
        // item.name = null // Compilation error -> cannot assign null
        // assertThat(item.name).isNull() // Runtime exception -> UninitializedPropertyAccessException("lateinit property name has not been initialized")

        item.name = "Hello World"
        assertThat(item.name).isEqualTo("Hello World")
    }

    @Test
    fun kotlinNullChecking() {
        var item: Item? = Item("Hello World")
        //variable.name.toUpperCase() // -> Compilation error, variable might be null which will result in an NPE

        val upperCase = item?.name?.toUpperCase()
        assertThat(upperCase).isEqualToIgnoringCase("HELLO WORLD")

        item?.name = null
        val upperCase2 = item?.name?.toUpperCase()
        assertThat(upperCase2).isEqualToIgnoringCase(null)

        item = null
        val upperCase3 = item?.name?.toUpperCase()
        assertThat(upperCase3).isEqualToIgnoringCase(null)

        //Tell kotlin that the variable is definitely not null
        try {
            //Will throw an error because it actually is null
            assertThat(item!!.name)
            fail("Kotlin should have thrown an error")
        } catch (e: Exception) {
            assertThat(true).isEqualTo(true)
        }
    }

    @Test
    fun lambdaSyntax() {
        // Lambda Syntax in Kotlin
        // NB: The last expression is used as a return
        val hello = { name: String ->
            "Hello $name"
        }
        assertThat(hello("World")).isEqualTo("Hello World")
    }

    @Test
    fun lambdaAsArgument() {
        // All three calls are semantically identical
        val sorted = listOf("aZ", "rB", "xA", "vU")
            .sortedBy({ str -> str.substring(1, 2) })
            // Braces can be omitted
            .sortedBy { str -> str.substring(1, 2) }
            // 'it' can be used on single-argument functions to refer to the first and only argument
            .sortedBy { it.substring(1, 2) }

        assertThat(sorted).isSortedAccordingTo { a, b -> a.substring(1, 2).compareTo(b.substring(1, 2)) }
    }

    // 'Static'

    object Something {
        val foo = "foo"
    }

    open class InheritFromMe {
        val fooBar: String = "fooBar"
    }

    companion object A : InheritFromMe() {
        const val BAR = "BAR"
    }

    @Test
    fun accessObject() {
        // Objects are accessed statically
        assertThat(Ex1KotlinBasics.Something.foo).isEqualTo("foo")

        // Companion object's properties are accessed directly on the parent
        assertThat(Ex1KotlinBasics.BAR).isEqualTo("BAR")

        // Can access inherited properties
        assertThat(Ex1KotlinBasics.fooBar).isEqualTo("fooBar")
    }

}