package at.fhj.ima.swengaprojekt.cloud.repository

import at.fhj.ima.swengaprojekt.cloud.dto.LoggingDto
import at.fhj.ima.swengaprojekt.cloud.entity.Logging
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LoggingRepository : JpaRepository<Logging, Int> {

    @Query("SELECT * FROM  logging", nativeQuery = true)
    fun getTable(): List<Logging>
}
