package at.fhj.ima.swengaprojekt.cloud.service

import at.fhj.ima.swengaprojekt.cloud.entity.Department
import at.fhj.ima.swengaprojekt.cloud.repository.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService(val departmentRepository: DepartmentRepository) {
    fun findAll(): List<Department> {
        return departmentRepository.findAll()
    }
}
