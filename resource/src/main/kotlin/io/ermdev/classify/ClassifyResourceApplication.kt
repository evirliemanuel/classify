package io.ermdev.classify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClassifyWebserviceApplication

fun main(args: Array<String>) {
    runApplication<ClassifyWebserviceApplication>(*args)
}
