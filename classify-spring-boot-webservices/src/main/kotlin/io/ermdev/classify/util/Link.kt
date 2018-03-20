package io.ermdev.classify.util

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.hateoas.Link

class Link: Link() {

    @JsonIgnore
    override fun getHreflang(): String {
        return super.getHreflang()
    }

    @JsonIgnore
    override fun getMedia(): String {
        return super.getMedia()
    }

    @JsonIgnore
    override fun getTitle(): String {
        return super.getTitle()
    }

    @JsonIgnore
    override fun getType(): String {
        return super.getType()
    }

    @JsonIgnore
    override fun getDeprecation(): String {
        return super.getDeprecation()
    }
}