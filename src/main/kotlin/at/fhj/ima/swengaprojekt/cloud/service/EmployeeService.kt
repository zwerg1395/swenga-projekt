package at.fhj.ima.swengaprojekt.cloud.service

import at.fhj.ima.swengaprojekt.cloud.dto.EmployeeDto
import at.fhj.ima.swengaprojekt.cloud.entity.Employee
import at.fhj.ima.swengaprojekt.cloud.repository.EmployeeRepository
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class EmployeeService(val employeeRepository: EmployeeRepository,
                      val mailSender: MailSender) {
    fun createNewEmployee(): EmployeeDto {
        val newEmployee = Employee()
        newEmployee.dayOfBirth = LocalDate.now()
        return convertEntityToDto(newEmployee)
    }

    fun findBySsn(ssn: Int): EmployeeDto {
        return convertEntityToDto(employeeRepository.findBySsn(ssn))
    }

    private fun convertEntityToDto(entity: Employee): EmployeeDto {
        return EmployeeDto(entity.id, entity.ssn, entity.firstName, entity.lastName, entity.dayOfBirth,
                entity.department, entity.projects,
                entity.files, entity.version)
    }

    @Transactional
    fun save(dto: EmployeeDto) {

        val employee = convertDtoToEntity(dto)

        employeeRepository.save(employee)

        val message = SimpleMailMessage()
        message.setTo("test@example.com")
        message.setSubject("Employee ${employee.ssn} was created or modified")
        message.setText("Employee ${employee.ssn} was created or modified")
        mailSender.send(message)
    }

    private fun convertDtoToEntity(dto: EmployeeDto): Employee {

        // logic that prevents specific roles/users from editing certain attributes could be added here

        val employee = Employee()
        employee.id = dto.id
        employee.ssn = dto.ssn
        employee.firstName = dto.firstName
        employee.lastName = dto.lastName
        employee.dayOfBirth = dto.dayOfBirth
        employee.department = dto.department
        employee.projects = dto.projects
        employee.files = dto.files?.filter { x -> x.id != null }
        employee.version = dto.version
        return employee
    }

    fun findByFirstnameOrLastname(search: String): List<Employee> {
        return employeeRepository.findByFirstnameOrLastname(search)
    }

    fun findAll(): List<Employee> {
        return employeeRepository.findAll()
    }

    fun delete(ssn: Int) {
        employeeRepository.delete(employeeRepository.findBySsn(ssn))
    }
}
