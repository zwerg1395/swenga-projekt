package at.fhj.ima.employee.employeemanager.repository

import at.fhj.ima.employee.employeemanager.entity.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : JpaRepository<File, Int> {
}
