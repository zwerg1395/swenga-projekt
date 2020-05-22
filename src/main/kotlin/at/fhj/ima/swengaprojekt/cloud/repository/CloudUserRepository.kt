package at.fhj.ima.swengaprojekt.cloud.repository

import at.fhj.ima.swengaprojekt.cloud.entity.CloudUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CloudUserRepository : JpaRepository<CloudUser, Int> {

    @Query("FROM CloudUser WHERE LOWER(firstName) LIKE CONCAT(LOWER(:search),'%') OR LOWER(lastName) LIKE CONCAT(LOWER(:search),'%')")
    fun findByFirstnameOrLastname(@Param("search") search: String): List<CloudUser>

    @Query("FROM CloudUser WHERE ssn = :ssn")
    fun findBySsn(@Param("ssn") ssn: Int): CloudUser

}
