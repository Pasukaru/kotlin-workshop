package com.github.pasukaru

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication(scanBasePackageClasses = arrayOf(Main::class))
//Disable DB related stuff for now
@EnableAutoConfiguration(exclude = arrayOf(
    DataSourceAutoConfiguration::class,
    DataSourceTransactionManagerAutoConfiguration::class,
    HibernateJpaAutoConfiguration::class
))
@EnableAsync
class Main

fun main(args: Array<String>) {
    SpringApplication.run(Main::class.java, *args)
}