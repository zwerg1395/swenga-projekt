package at.fhj.ima.swengaprojekt.cloud.dto

import at.fhj.ima.swengaprojekt.cloud.entity.Logging
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


class LoggingDto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var user: String? = null,
    var action: String? = null,
    var date: String? = null
) : Comparable<Logging>, Serializable {
    override fun compareTo(other: Logging): Int {
        return compareValues(id, other.id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Logging
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
