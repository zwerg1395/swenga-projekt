package at.fhj.ima.employee.employeemanager.converter

import at.fhj.ima.employee.employeemanager.entity.Project
import at.fhj.ima.employee.employeemanager.repository.ProjectRepository
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class StringToProjectConverter(val projectRepository: ProjectRepository) : Converter<String, Project?> {
    override fun convert(source: String): Project? {
        if (source.isNullOrBlank()) {
            return null
        }
        return projectRepository.getOne(Integer.parseInt(source));
    }
}
