package at.fhj.ima.swengaprojekt.cloud.service

import at.fhj.ima.swengaprojekt.cloud.dto.LoggingDto
import at.fhj.ima.swengaprojekt.cloud.entity.Logging
import at.fhj.ima.swengaprojekt.cloud.entity.User
import at.fhj.ima.swengaprojekt.cloud.entity.UserDto
import at.fhj.ima.swengaprojekt.cloud.repository.LoggingRepository
import at.fhj.ima.swengaprojekt.cloud.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoggingService(val loggingRepository: LoggingRepository) {

    @Transactional
    fun save(dto: LoggingDto) {

        val logging = convertDtoToEntity(dto)

        loggingRepository.save(logging)
    }

    private fun convertEntityToDto(entity: Logging): LoggingDto {
        return LoggingDto(entity.id, entity.user, entity.action, entity.date)
    }

    private fun convertTableToString(): LoggingDto {
        return LoggingDto()
    }

    private fun convertDtoToEntity(dto: LoggingDto): Logging {

        val logging = Logging()
        logging.id = dto.id
        logging.user = dto.user
        logging.action = dto.action
        logging.date = dto.date
        return logging
    }
}