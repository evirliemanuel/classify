package io.ermdev.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_role")
class Role(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        var name: String = "",

        @ManyToMany(mappedBy = "roles", cascade = [CascadeType.REMOVE])
        var users: MutableSet<User> = HashSet())