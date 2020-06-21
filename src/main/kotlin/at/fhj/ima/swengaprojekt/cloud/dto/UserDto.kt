package at.fhj.ima.swengaprojekt.cloud.entity

import java.io.Serializable
import javax.persistence.*

class UserDto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(nullable = false, unique = true)
    var username: String? = null,
    var password: String? = null,
    var passwordOld: String? = null,
    @Enumerated(EnumType.STRING)
    var role: UserRole? = null,
    var files: List<File>? = null,
    @ManyToOne
    var subscription: Subscription = Subscription()
) : Comparable<User>, Serializable {
    override fun compareTo(other: User): Int {
        return compareValues(id, other.id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as User
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
