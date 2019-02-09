package org.breizhcamp.talk.tweetmockserver.service

import com.thedeanda.lorem.LoremIpsum
import org.breizhcamp.talk.tweetmockserver.model.Tweet
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class TweetService {

    fun generateTweets(number: Int): List<Tweet> {

        return (1 until number)
                .map { id ->
                    generateTweet(id.toLong())
                }
    }

    fun generateTweet(id: Long): Tweet {
        val loremIpsum = LoremIpsum.getInstance()

        return Tweet(
                id = id,
                hashtag = HASHTAGS[Random().nextInt(HASHTAGS.size)],
                content = loremIpsum.getWords(5, 10),
                createdAt = Instant.now()
        )
    }

    companion object {
        private val HASHTAGS = listOf("java", "kotlin", "javascript", "php")
    }
}
