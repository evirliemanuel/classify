package io.ermdev.classify.hateoas

import com.fasterxml.jackson.annotation.JsonAlias
import io.ermdev.classify.hateoas.Link

open class ResourceSupport(@JsonAlias("_links")
                           val links: MutableList<Link> = ArrayList()) {

    fun add(link: Link) = links.add(link)
}