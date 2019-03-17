package org.breizhcamp.talk.tweetmockserver.controller

import org.breizhcamp.talk.tweetmockserver.model.Tweet
import org.breizhcamp.talk.tweetmockserver.service.TweetService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono
import java.time.Duration

@RestController
class TweetController(private val tweetService: TweetService) {

    private val logger = LoggerFactory.getLogger(TweetController::class.java)

    @GetMapping("/tweets")
    fun getTweets(): Flux<Tweet> {
        logger.info("GET /tweets")
        return tweetService.generateTweets(50)
                .toFlux()
                .log()
    }

    @GetMapping("/tweets/{id}")
    fun getTweet(@PathVariable id: Long, serverHttpResponse: ServerHttpResponse): Mono<Tweet> {
        logger.info("GET /tweets/{}", id)
        return if (id > 10000) {
            serverHttpResponse.statusCode = HttpStatus.NOT_FOUND
            Mono.empty()
        } else {
            tweetService.generateTweet(id)
                    .toMono()
                    .log()
        }
    }

    @GetMapping(path = ["/tweets"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getStreamTweets(): Flux<Tweet> {
        logger.info("GET /tweets - Accept: {}", MediaType.TEXT_EVENT_STREAM_VALUE)
        return Flux.interval(Duration.ofMillis(500))
                .map { sequence ->
                    tweetService.generateTweet(sequence)
                }
                .log()
    }
}
