package at.fhj.ima.swengaprojekt.cloud.controller

import at.fhj.ima.swengaprojekt.cloud.dto.LoggingDto
import at.fhj.ima.swengaprojekt.cloud.entity.File
import at.fhj.ima.swengaprojekt.cloud.entity.UserDto
import at.fhj.ima.swengaprojekt.cloud.entity.UserRole
import at.fhj.ima.swengaprojekt.cloud.repository.FileRepository
import at.fhj.ima.swengaprojekt.cloud.repository.UserRepository
import at.fhj.ima.swengaprojekt.cloud.service.FileService
import at.fhj.ima.swengaprojekt.cloud.service.LoggingService
import at.fhj.ima.swengaprojekt.cloud.service.UserService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.validation.Valid


@Controller
class CloudServiceController (val userService: UserService, val fileRepository: FileRepository, val loggingService: LoggingService, val fileService: FileService, val userRepository: UserRepository
) {

    @RequestMapping("/cloud", method = [RequestMethod.GET])
    fun showCloud(model: Model, @RequestParam(required = true) username: String, @RequestParam(required = false) error: String?): String {

        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val currentPrincipalName: String = authentication.getName()
        val hasAdminRole: Boolean = authentication.authorities.stream().anyMatch { r: GrantedAuthority? -> r!!.authority == "ROLE_ADMIN" }

        if (username != currentPrincipalName){ // "&& !hasAdminRole"? gdpr?
            return "redirect:/cloud?username=" + currentPrincipalName
        }

        model.set("user", userService.findByUsername(username))

        return "cloud"
    }

    @RequestMapping("/saveFile", method = [RequestMethod.POST])
    fun saveFile(@ModelAttribute("user") @Valid user: UserDto, logging: LoggingDto, bindingResult: BindingResult): String {

        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val currentPrincipalName: String = authentication.getName()


        user.id = userService.findByUsername(currentPrincipalName).id
        user.username = userService.findByUsername(currentPrincipalName).username
        user.password = userService.findByUsername(currentPrincipalName).password
        user.passwordOld = userService.findByUsername(currentPrincipalName).passwordOld
        user.role = userService.findByUsername(currentPrincipalName).role
        //user.files = userService.findByUsername(currentPrincipalName).files
        user.subscription = userService.findByUsername(currentPrincipalName).subscription

        logging.user = user.username
        logging.action = "File Uploaded."
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)
        logging.date = formatted.toString()

        loggingService.save(logging)

        /*
        if (authentication.authorities.stream().anyMatch { r: GrantedAuthority? -> r!!.authority == "ROLE_ADMIN" })
        {
            user.role = UserRole.ROLE_ADMIN
        }

        if (authentication.authorities.stream().anyMatch { r: GrantedAuthority? -> r!!.authority == "ROLE_USER" })
        {
            user.role = UserRole.ROLE_USER
        }
         */

        //if(userService.findByUsername(currentPrincipalName).subscription.limit )

        var fileSizes: Long = 0;

        if (user.files != null){
            for(elem in user.files!!){
                fileSizes = fileSizes + (elem.size ?: 0)
                //System.out.println(elem.size ?: 0)
                //System.out.println(fileSizes)
            }
        }

        if (fileSizes >= user.subscription.fileLimit){
            //bindingResult.rejectValue("files", "files.tooLarge", "Upload Limit reached.\nDelete existing files or change subscription.");
            logging.user = user.username
            logging.action = "File Upload failed. Storage space exceeded by " + (fileSizes - user.subscription.fileLimit).toString() + " bytes."
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            logging.date = formatted.toString()

            loggingService.save(logging)

            return "redirect:/cloud?username=" + currentPrincipalName + "&error=storage"
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/cloud?username=" + currentPrincipalName
        }
        try {
            userService.save(user)

            var allFiles: List<File> = fileRepository.findAll()
            var userFiles = userRepository.getAllUserFiles()
            if (allFiles != null && userFiles != null){
                for(elem in allFiles!!){
                    if (!userFiles.contains(elem.id)){
                        fileService.delete(elem.id!!)
                    }
                }
            }



            //var allFiles: List<File> = fileRepository.findAll()
            var currentUserFiles = userService.findByUsername(currentPrincipalName).files
            var tempFiles: MutableList<File> = mutableListOf<File>()


            if (allFiles != null && currentUserFiles != null){
                for(elem in currentUserFiles!!){
                    if (allFiles.contains(elem)){
                        tempFiles.add(elem)
                    }
                }
            }

            user.files = tempFiles

            userService.save(user)

        } catch (dive: DataIntegrityViolationException) {
            if (dive.message.orEmpty().contains("id_UK")) {
                bindingResult.rejectValue("id", "id.alreadyInUse", "ID already in use.");
                return "redirect:/cloud?username=" + currentPrincipalName
            } else {
                throw dive;
            }
        }
        return "redirect:/cloud?username=" + currentPrincipalName
    }

}