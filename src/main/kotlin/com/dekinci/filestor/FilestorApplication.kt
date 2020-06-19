package com.dekinci.filestor

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [
    SecurityAutoConfiguration::class,
    ManagementWebSecurityAutoConfiguration::class
])
class FilestorApplication

fun main(args: Array<String>) {
    runApplication<FilestorApplication>(*args)
}
