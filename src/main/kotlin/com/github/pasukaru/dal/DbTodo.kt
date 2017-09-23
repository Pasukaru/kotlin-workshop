package com.github.pasukaru.dal

import java.util.*
import javax.persistence.Column
import javax.persistence.JoinColumn

class DbTodo : DbModel {

    @Column(name = "id")
    lateinit var id: UUID

    @Column(name = "namne")
    var name: String? = null

    @JoinColumn(name = "user_id")
    lateinit var user: DbUser
}