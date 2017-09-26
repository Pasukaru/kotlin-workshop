package com.github.pasukaru

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class Ex4KotlinDefaultExtensions {

    data class Item(val id: UUID, val name: String) {
        constructor(name: String) : this(UUID.randomUUID(), name)
    }

    @Test
    fun associateBy() {
        // Create fixtures
        val item1 = Item("test1")
        val item2 = Item("test2")
        val fixtures = listOf(item1, item2)

        // Get fixtures from db
        val items = fixtures.map { it.copy() }

        // Verify result
        val mapped = items.associateBy { it.id }
        assertThat(mapped).hasSameSizeAs(fixtures)
        fixtures.forEach { fixture ->
            assertThat(mapped[fixture.id])
                .isNotNull()
                .isNotSameAs(fixture)
                .isEqualTo(fixture)
        }
    }

}