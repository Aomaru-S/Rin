package com.rinats.rin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class RinApplication

fun main(args: Array<String>) {
    runApplication<RinApplication>(*args)
}
