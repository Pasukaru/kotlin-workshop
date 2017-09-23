@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package com.github.pasukaru

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

class Ex7Delegation {

    interface Counter {
        fun getCount(): Int
        fun count()
        fun countTwice()
    }

    class CounterImpl : Counter {
        private var _count = 0
        override fun count() {
            _count++
        }

        override fun countTwice() {
            _count += 2
        }

        override fun getCount(): Int {
            return _count
        }
    }

    class Proxy(p: Counter) : Counter by p {
        override fun countTwice() {}
    }

    @Test
    fun proxyExample() {
        val proxy = Proxy(CounterImpl()) as Counter

        proxy.count()
        assertThat(proxy.getCount()).isEqualTo(1)
        proxy.count()
        assertThat(proxy.getCount()).isEqualTo(2)
        proxy.count()
        assertThat(proxy.getCount()).isEqualTo(3)

        proxy.countTwice()
        assertThat(proxy.getCount()).isEqualTo(3)
    }


    data class LazyExample(var firstName: String, var lastName: String) {
        var computed = 0
        val fullName: String by lazy {
            computed++
            "$firstName $lastName"
        }
    }

    @Test
    fun lazyPropertyExample() {
        // Lazy property will only be computed once, when it's first accessed:
        val item = LazyExample("John", "Doe")
        assertThat(item.computed).isEqualTo(0)

        assertThat(item.fullName).isEqualTo("John Doe")
        assertThat(item.computed).isEqualTo(1)
        assertThat(item.fullName).isEqualTo("John Doe")
        assertThat(item.computed).isEqualTo(1)

        // Lazy does NOT keep track of its dependencies:
        item.firstName = "Peter"
        assertThat(item.fullName).isEqualTo("John Doe")
        assertThat(item.computed).isEqualTo(1)
    }

    class ObservableExample(firstName: String, lastName: String) {
        var firstNameComputed = 0
        var lastNameComputed = 0
        var fullName = ""

        var firstName: String by Delegates.observable(firstName) { prop, old, new ->
            firstNameComputed++
            updateFullName()
        }

        var lastName: String by Delegates.observable(lastName) { prop, old, new ->
            lastNameComputed++
            updateFullName()
        }

        init {
            updateFullName()
        }

        private fun updateFullName() {
            fullName = "$firstName $lastName"
        }
    }

    @Test
    fun observableExample() {
        val item = ObservableExample("John", "Doe")
        assertThat(item.fullName).isEqualTo("John Doe")
        assertThat(item.firstNameComputed).isEqualTo(0)
        assertThat(item.lastNameComputed).isEqualTo(0)

        item.firstName = "Peter"
        assertThat(item.fullName).isEqualTo("Peter Doe")
        assertThat(item.firstNameComputed).isEqualTo(1)
        assertThat(item.lastNameComputed).isEqualTo(0)

        item.firstName = "Max"
        assertThat(item.fullName).isEqualTo("Max Doe")
        assertThat(item.firstNameComputed).isEqualTo(2)
        assertThat(item.lastNameComputed).isEqualTo(0)

        item.lastName = "Smith"
        assertThat(item.fullName).isEqualTo("Max Smith")
        assertThat(item.firstNameComputed).isEqualTo(2)
        assertThat(item.lastNameComputed).isEqualTo(1)
    }

}