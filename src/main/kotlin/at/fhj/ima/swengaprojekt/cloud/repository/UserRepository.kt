package at.fhj.ima.swengaprojekt.cloud.repository

import at.fhj.ima.swengaprojekt.cloud.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    @Query("FROM User where username = :username")
    fun findByUsername(@Param("username") username: String): User
    @Query("SELECT files_id FROM swenga.user_files where files_id IS NOT NULL", nativeQuery = true)
    fun getAllUserFiles(): List<Int>
}
