package at.fhj.ima.employee.employeemanager.repository

import at.fhj.ima.employee.employeemanager.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Int> {

    @Query("FROM Employee WHERE LOWER(firstName) LIKE CONCAT(LOWER(:search),'%') OR LOWER(lastName) LIKE CONCAT(LOWER(:search),'%')")
    fun findByFirstnameOrLastname(@Param("search") search: String): List<Employee>

    @Query("FROM Employee WHERE ssn = :ssn")
    fun findBySsn(@Param("ssn") ssn: Int): Employee

}
