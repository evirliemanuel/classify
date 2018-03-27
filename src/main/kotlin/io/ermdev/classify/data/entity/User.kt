package io.ermdev.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_user")
class User(@Id
           @GeneratedValue(strategy = GenerationType.AUTO)
           var id: Long = 0,

           @Column(unique = true)
           var username: String = "",

           var password: String = "",

           @ManyToMany(cascade = [CascadeType.PERSIST])
           @JoinTable(name = "tbl_user_role", joinColumns = [JoinColumn(name = "user_id", nullable = false)],
                   inverseJoinColumns = [JoinColumn(name = "role_id", nullable = false)])
           var roles: MutableSet<Role> = HashSet(),

           @OneToOne(mappedBy = "user")
           var student: Student? = null,

           @OneToOne(mappedBy = "user")
           var teacher: Teacher? = null)
