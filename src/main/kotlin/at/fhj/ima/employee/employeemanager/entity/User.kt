package at.fhj.ima.employee.employeemanager.entity

import java.io.Serializable
import javax.persistence.*

enum class UserRole {
    ROLE_USER,
    ROLE_ADMIN
}

@Entity
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        @Column(nullable = false, unique = true)
        var username: String? = null,
        var password: String? = null,
        @Enumerated(EnumType.STRING)
        var role: UserRole? = null
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
