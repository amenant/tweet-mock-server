package org.breizhcamp.talk.tweetmockserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpringWebfluxApplication

fun main(args: Array<String>) {
    SpringApplication.run(SpringWebfluxApplication::class.java, *args)
}
