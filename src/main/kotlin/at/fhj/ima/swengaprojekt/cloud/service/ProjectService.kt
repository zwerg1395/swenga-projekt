package at.fhj.ima.swengaprojekt.cloud.service

import at.fhj.ima.swengaprojekt.cloud.entity.Project
import at.fhj.ima.swengaprojekt.cloud.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(val projectRepository: ProjectRepository) {
    fun findAll(): List<Project> {
        return projectRepository.findAll()
    }
}
