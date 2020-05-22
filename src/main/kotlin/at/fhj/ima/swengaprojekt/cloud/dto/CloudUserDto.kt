package at.fhj.ima.swengaprojekt.cloud.dto

import at.fhj.ima.swengaprojekt.cloud.entity.Department
import at.fhj.ima.swengaprojekt.cloud.entity.Employee
import at.fhj.ima.swengaprojekt.cloud.entity.File
import at.fhj.ima.swengaprojekt.cloud.entity.Project
import java.io.Serializable
import java.time.LocalDate
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import javax.validation.constraints.Size

class CloudUserDto(
    var id: Int? = null,
    @field:NotNull()
        var ssn: Int? = null,
    @field:Size(min = 2, max = 240)
        var firstName: String? = null,
    @field:Size(min = 2, max = 240)
        var lastName: String? = null,
    @field:Past
        var dayOfBirth: LocalDate? = null,
    var department: Department? = null,
    var projects: Set<Project>? = null,
    var files: List<File>? = null,
    var version: Int? = null
) : Comparable<Employee>, Serializable {
    override fun compareTo(other: Employee): Int {
        return compareValues(ssn, other.ssn)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Employee
        if (ssn != other.ssn) return false
        return true
    }

    override fun hashCode(): Int {
        return ssn.hashCode()
    }
}
