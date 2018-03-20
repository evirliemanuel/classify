package io.ermdev.classify.util

open class ResourceSupport(private val links: MutableList<Link> = ArrayList()) {

    fun add(link: Link) = links.add(link)
}