package at.fhj.ima.employee.employeemanager.service

import at.fhj.ima.employee.employeemanager.entity.Project
import at.fhj.ima.employee.employeemanager.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(val projectRepository: ProjectRepository) {
    fun findAll(): List<Project> {
        return projectRepository.findAll()
    }
}
