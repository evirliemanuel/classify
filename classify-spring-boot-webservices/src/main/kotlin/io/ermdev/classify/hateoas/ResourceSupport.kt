package io.ermdev.classify.hateoas

import com.fasterxml.jackson.annotation.JsonProperty

open class ResourceSupport(@JsonProperty("_links")
                           val links: MutableList<Link> = ArrayList()) {

    fun add(link: Link) = links.add(link)
}