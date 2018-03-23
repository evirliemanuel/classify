package io.ermdev.classify.hateoas

import com.fasterxml.jackson.annotation.JsonProperty

open class ResourceSupport(@JsonProperty("_links")
                           private val _links: MutableList<Link> = ArrayList()) {

    fun add(link: Link) = _links.add(link)
}