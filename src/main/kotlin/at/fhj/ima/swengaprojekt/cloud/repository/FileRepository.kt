package at.fhj.ima.swengaprojekt.cloud.repository

import at.fhj.ima.swengaprojekt.cloud.entity.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : JpaRepository<File, Int> {
}
