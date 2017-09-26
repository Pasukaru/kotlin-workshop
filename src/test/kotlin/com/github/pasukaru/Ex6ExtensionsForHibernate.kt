package com.github.pasukaru

import com.github.pasukaru.dal.DbModel
import com.github.pasukaru.dal.DbTodo
import com.github.pasukaru.dal.DbUser
import org.assertj.core.api.Assertions
import org.hibernate.jpa.criteria.path.AbstractFromImpl
import org.junit.jupiter.api.Test
import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.*
import kotlin.reflect.KProperty1

class WithoutExtensions : Specification<DbTodo> {
    override fun toPredicate(root: Root<DbTodo>, query: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate {
        val userJoin = root.join<DbTodo, DbUser>("user")
        return cb.equal(userJoin.get<DbUser>("id"), UUID.randomUUID())
    }
}

class WithExtensions : Specification<DbTodo> {
    override fun toPredicate(root: Root<DbTodo>, query: CriteriaQuery<*>?, cb: CriteriaBuilder?): Predicate {
        val userJoin = root.join(DbTodo::user)
        return cb!!.equal(userJoin.get(DbUser::id), UUID.randomUUID())
    }
}

fun <Y> Path<*>.get(attribute: KProperty1<*, Y>): Path<Y> {
    return this.get(attribute.name)
}

fun <FROM : DbModel, TO : DbModel?> From<*, FROM>.join(property: KProperty1<FROM, TO>, type: JoinType = AbstractFromImpl.DEFAULT_JOIN_TYPE): Join<FROM, TO> {
    return this.join(property.name, type)
}

class Ex6ExtensionsForHibernate {

    @Test
    fun useExtension() {
        Assertions.assertThat(WithoutExtensions()).isNotNull()
        Assertions.assertThat(WithExtensions()).isNotNull()
    }

}