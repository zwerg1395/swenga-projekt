package at.fhj.ima.employee.employeemanager.service

import at.fhj.ima.employee.employeemanager.entity.Department
import at.fhj.ima.employee.employeemanager.repository.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService(val departmentRepository: DepartmentRepository) {
    fun findAll(): List<Department> {
        return departmentRepository.findAll()
    }
}
