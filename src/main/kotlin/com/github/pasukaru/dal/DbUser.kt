package com.github.pasukaru.dal

import java.util.*
import javax.persistence.Column
import javax.persistence.OneToMany

class DbUser : DbModel {

    @Column(name = "the_id")
    lateinit var id: UUID

    @Column(name = "firstname")
    var name: String? = null

    @OneToMany(mappedBy = "customer")
    var todos = mutableSetOf<DbTodo>()

}