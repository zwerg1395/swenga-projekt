package at.fhj.ima.swengaprojekt.cloud.service

import at.fhj.ima.swengaprojekt.cloud.dto.CloudUserDto
import at.fhj.ima.swengaprojekt.cloud.entity.CloudUser
import at.fhj.ima.swengaprojekt.cloud.repository.CloudUserRepository
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class CloudUserService(val cloudUserRepository: CloudUserRepository,
                       val mailSender: MailSender) {
    fun createNewCloudUser(): CloudUserDto {
        val newCloudUser = CloudUser()
        newCloudUser.dayOfBirth = LocalDate.now()
        return convertEntityToDto(newCloudUser)
    }

    fun findBySsn(ssn: Int): CloudUserDto {
        return convertEntityToDto(cloudUserRepository.findBySsn(ssn))
    }

    private fun convertEntityToDto(entity: CloudUser): CloudUserDto {
        return CloudUserDto(entity.id, entity.ssn, entity.firstName, entity.lastName, entity.dayOfBirth,
                entity.department, entity.projects,
                entity.files, entity.version)
    }

    @Transactional
    fun save(dto: CloudUserDto) {

        val cloudUser = convertDtoToEntity(dto)

        cloudUserRepository.save(cloudUser)

        val message = SimpleMailMessage()
        message.setTo("test@example.com")
        message.setSubject("CloudUser ${cloudUser.ssn} was created or modified")
        message.setText("CloudUser ${cloudUser.ssn} was created or modified")
        mailSender.send(message)
    }

    private fun convertDtoToEntity(dto: CloudUserDto): CloudUser {

        // logic that prevents specific roles/users from editing certain attributes could be added here

        val cloudUser = CloudUser()
        cloudUser.id = dto.id
        cloudUser.ssn = dto.ssn
        cloudUser.firstName = dto.firstName
        cloudUser.lastName = dto.lastName
        cloudUser.dayOfBirth = dto.dayOfBirth
        cloudUser.department = dto.department
        cloudUser.projects = dto.projects
        cloudUser.files = dto.files?.filter { x -> x.id != null }
        cloudUser.version = dto.version
        return cloudUser
    }

    fun findByFirstnameOrLastname(search: String): List<CloudUser> {
        return cloudUserRepository.findByFirstnameOrLastname(search)
    }

    fun findAll(): List<CloudUser> {
        return cloudUserRepository.findAll()
    }

    fun delete(ssn: Int) {
        cloudUserRepository.delete(cloudUserRepository.findBySsn(ssn))
    }
}
