package org.breizhcamp.talk.tweetmockserver.controller

import org.breizhcamp.talk.tweetmockserver.model.Tweet
import org.breizhcamp.talk.tweetmockserver.service.TweetService
import org.springframework.beans.factory.annotation.Autowired
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
class TweetController {

    @Autowired
    private lateinit var tweetService: TweetService

    @GetMapping("/tweet")
    fun getTweets(): Flux<Tweet> {
        return tweetService.generateTweets(50).toFlux()
    }

    @GetMapping("/tweet/{id}")
    fun getTweet(@PathVariable id: Long): Mono<Tweet> {
        return tweetService.generateTweet(id).toMono()
    }

    @GetMapping(path = ["/tweet-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getStreamTweets(): Flux<Tweet> {
        return Flux.interval(Duration.ofMillis(500))
                .map { sequence ->
                    tweetService.generateTweet(sequence)
                }
    }
}