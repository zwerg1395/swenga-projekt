package at.fhj.ima.swengaprojekt.cloud.service

import at.fhj.ima.swengaprojekt.cloud.entity.User
import at.fhj.ima.swengaprojekt.cloud.entity.UserDto
import at.fhj.ima.swengaprojekt.cloud.repository.UserRepository
import org.springframework.mail.MailSender
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(val userRepository: UserRepository,
                  val mailSender: MailSender) {
    fun createNewUser(): UserDto {
        val newUser = User()
        return convertEntityToDto(newUser)
    }

    fun findByUsername(username: String): UserDto {
        return convertEntityToDto(userRepository.findByUsername(username))
    }


    private fun convertEntityToDto(entity: User): UserDto {
        return UserDto(entity.id, entity.username, entity.password, entity.passwordOld, entity.role, entity.files, entity.subscription)
    }

    @Transactional
    fun save(dto: UserDto) {

        val user = convertDtoToEntity(dto)

        userRepository.save(user)

        /*
        val message = SimpleMailMessage()
        message.setTo("test@example.com")
        message.setSubject("CloudUser ${user.id} was created or modified")
        message.setText("CloudUser ${user.id} was created or modified")
        mailSender.send(message)
         */
    }



    private fun convertDtoToEntity(dto: UserDto): User {

        // logic that prevents specific roles/users from editing certain attributes could be added here

        val user = User()
        user.id = dto.id
        user.username = dto.username
        user.password = dto.password
        user.passwordOld = dto.passwordOld
        user.role = dto.role
        user.files = dto.files?.filter { x -> x.id != null }
        user.subscription = dto.subscription
        return user
    }


    fun delete(username: String) {
        userRepository.delete(userRepository.findByUsername(username))
    }

    fun deleteFileAssociation(username: String, fileId: Int)
    {
        var user = findByUsername(username)
        //user.files.find { fileId.equals(user.files) }
    }
}
