package at.fhj.ima.swengaprojekt.cloud.entity

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import javax.validation.constraints.Size


@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "ssn_UK", columnNames = ["ssn"])])
class CloudUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        @Column(nullable = false)
        @field:NotNull()
        var ssn: Int? = null,
        @field:Size(min = 2, max = 240)
        var firstName: String? = null,
        @field:Size(min = 2, max = 240)
        var lastName: String? = null,
        @field:Past
        var dayOfBirth: LocalDate? = null,
        @ManyToOne
        var department: Department? = null,
        @ManyToMany
        var projects: Set<Project>? = null,
        @ManyToMany(fetch = FetchType.EAGER)
        var files: List<File>? = null,
        @Version
        var version: Int? = null
) : Comparable<CloudUser>, Serializable {
    override fun compareTo(other: CloudUser): Int {
        return compareValues(ssn, other.ssn)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as CloudUser
        if (ssn != other.ssn) return false
        return true
    }

    override fun hashCode(): Int {
        return ssn.hashCode()
    }
}
