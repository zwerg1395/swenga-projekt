package at.fhj.ima.swengaprojekt.cloud.controller

import at.fhj.ima.swengaprojekt.cloud.dto.LoggingDto
import at.fhj.ima.swengaprojekt.cloud.entity.File
import at.fhj.ima.swengaprojekt.cloud.entity.User
import at.fhj.ima.swengaprojekt.cloud.entity.UserDto
import at.fhj.ima.swengaprojekt.cloud.service.LoggingService
import at.fhj.ima.swengaprojekt.cloud.service.UserService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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
class ChangeProfileController (val userService: UserService, val loggingService: LoggingService
) {
    @RequestMapping("/changeProfile", method = [RequestMethod.GET])
    fun changeProfile(model: Model, @RequestParam(required = true) username: String): String {

        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val currentPrincipalName: String = authentication.getName()
        val hasAdminRole: Boolean = authentication.authorities.stream().anyMatch { r: GrantedAuthority? -> r!!.authority == "ROLE_ADMIN" }

        if (username != currentPrincipalName){ // "&& !hasAdminRole"? gdpr?
            return "redirect:/changeProfile?username=" + currentPrincipalName
        }

        //model.set("user", userService.findByUsername(username))
        model.set("user", User(subscription = userService.findByUsername(username).subscription))

        return "changeProfile"
    }

    @RequestMapping("/updateProfile", method = [RequestMethod.POST])
    fun updateProfile(@ModelAttribute("user") @Valid user: UserDto, bindingResult: BindingResult, logging: LoggingDto): String {

        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val currentPrincipalName: String = authentication.getName()

        var userPW = userService.findByUsername(currentPrincipalName).password

        //user.passwordOld = BCryptPasswordEncoder().encode(user.passwordOld)

        if (!(user.password == "" && user.passwordOld == "")){
            if(!BCryptPasswordEncoder().matches(user.passwordOld, userPW)){
                bindingResult.rejectValue("passwordOld", "password.noMatch", "Wrong password.");
                return "changeProfile"
            }
        }

        if (user.password == "" && user.passwordOld != ""){
            bindingResult.rejectValue("password", "password.empty", "New password may not be empty.");
            return "changeProfile"
        }

        if (user.subscription == null){
            bindingResult.rejectValue("subscription", "subscription.empty", "Subscription required.");
            return "changeProfile"
        }

        if (bindingResult.hasErrors()) {
            return "changeProfile"
        }
        try {
            user.id = userService.findByUsername(currentPrincipalName).id
            user.username = currentPrincipalName
            user.password = BCryptPasswordEncoder().encode(user.password)
            user.passwordOld = BCryptPasswordEncoder().encode(user.passwordOld)
            user.role = userService.findByUsername(currentPrincipalName).role
            user.files = userService.findByUsername(currentPrincipalName).files
            userService.save(user)

            logging.user = currentPrincipalName
            logging.action = "Profile was updated."
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            logging.date = formatted.toString()

            loggingService.save(logging)
        } catch (dive: DataIntegrityViolationException) {
            if (dive.message.orEmpty().contains("id_UK")) {
                bindingResult.rejectValue("id", "id.alreadyInUse", "ID already in use.");
                return "changeProfile"
            } else {
                throw dive;
            }
        }
        return "redirect:/cloud?username=" + user.username
    }


}