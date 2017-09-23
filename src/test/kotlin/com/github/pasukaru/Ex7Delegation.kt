package com.github.pasukaru

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
    fun associateBy() {
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

}