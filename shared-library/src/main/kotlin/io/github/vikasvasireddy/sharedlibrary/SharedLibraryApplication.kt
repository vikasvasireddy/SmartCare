package io.github.vikasvasireddy.sharedlibrary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SharedLibraryApplication

fun main(args: Array<String>) {
    runApplication<SharedLibraryApplication>(*args)
}
