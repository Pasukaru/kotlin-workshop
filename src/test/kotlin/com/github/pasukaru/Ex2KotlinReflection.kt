package com.github.pasukaru

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*
import javax.persistence.Column
import javax.persistence.Table
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaField

class Ex2KotlinReflection {

    @Table(name = "the_item")
    class Item(@Column(name = "the_id") var id: UUID) {
        fun doSomething(): UUID {
            return id
        }

        fun doSomethingWithArg(uuid: UUID): UUID {
            return uuid
        }
    }

    @Test
    fun classReflections() {
        val ref = Item::class
        // Name of class
        assertThat(ref.simpleName).isEqualTo("Item")
        // Get an annotation of the class
        assertThat(ref.findAnnotation<Table>()!!.name).isEqualTo("the_item")
    }

    @Test
    fun propertyReflections() {
        val ref = Item::id
        assertThat(ref.name).isEqualTo("id")
        assertThat(Item::id.findAnnotation<Column>()).isNull() // Kotlin bug?
        //Workaround:
        assertThat(Item::id.javaField!!.getAnnotationsByType(Column::class.java)!!.first().name).isEqualTo("the_id")

        val id = UUID.randomUUID()
        val item = Item(id)

        // Getter
        assertThat(ref.get(item)).isEqualTo(id)

        // Setter
        val newId = UUID.randomUUID()
        Item::id.set(item, newId)
        assertThat(item.id).isEqualTo(newId)
    }

    @Test
    fun methodReflections() {
        val uuid = UUID.randomUUID()
        val item = Item(uuid)
        Assertions.assertThat(Item::doSomething.invoke(item)).isEqualTo(uuid)

        val uuid2 = UUID.randomUUID()
        assertThat(Item::doSomethingWithArg.invoke(item, uuid2)).isEqualTo(uuid2)
        // assertThat(Item::doSomethingWithArg.invoke(item)).isEqualTo(uuid2) // Compilation error
    }

}