package org.breizhcamp.talk.tweetmockserver.controller

import org.breizhcamp.talk.tweetmockserver.model.Tweet
import org.breizhcamp.talk.tweetmockserver.service.TweetService
import org.springframework.http.MediaType
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

    @GetMapping("/tweets")
    fun getTweets(): Flux<Tweet> {
        return tweetService.generateTweets(50).toFlux()
    }

    @GetMapping("/tweets/{id}")
    fun getTweet(@PathVariable id: Long): Mono<Tweet> {
        return tweetService.generateTweet(id).toMono()
    }

    @GetMapping(path = ["/tweets"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getStreamTweets(): Flux<Tweet> {
        return Flux.interval(Duration.ofMillis(500))
                .map { sequence ->
                    tweetService.generateTweet(sequence)
                }
    }
}
