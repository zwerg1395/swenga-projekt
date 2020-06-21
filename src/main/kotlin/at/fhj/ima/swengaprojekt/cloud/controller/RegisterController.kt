package at.fhj.ima.swengaprojekt.cloud.controller

import at.fhj.ima.swengaprojekt.cloud.dto.LoggingDto
import at.fhj.ima.swengaprojekt.cloud.entity.*
import at.fhj.ima.swengaprojekt.cloud.service.*
import org.springframework.dao.DataIntegrityViolationException
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
class RegisterController (val userService: UserService, val loggingService: LoggingService
) {

    @RequestMapping("/register", method = [RequestMethod.GET])
    fun register(model: Model, @RequestParam(required = false) id: Int?): String {
        if (id != null) {
            //model.set("user", userService.findById(id))
        } else {
            model.set("user", User())
        }
        return "register"
    }

    @RequestMapping("/registerUser", method = [RequestMethod.POST])
    fun registerUser(@ModelAttribute("user") @Valid user: UserDto, bindingResult: BindingResult, model: Model, logging: LoggingDto): String {

        if (user.username == ""){
            bindingResult.rejectValue("username", "username.isEmpty", "Username required.");
            return "register"
        }

        if (user.password == ""){
            bindingResult.rejectValue("password", "password.isEmpty", "Password required.");
            return "register"
        }

        if (user.subscription == null){
            bindingResult.rejectValue("subscription", "subscription.isEmpty", "Subscription required.");
            return "register"
        }

        if (bindingResult.hasErrors()) {
            return "register"
        }
        try {
            user.password = BCryptPasswordEncoder().encode(user.password)
            user.role = UserRole.ROLE_USER
            userService.save(user)

            logging.user = user.username
            logging.action = "User " + user.username + " registered with " + user.subscription.name + " subscription."
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            logging.date = formatted.toString()

            loggingService.save(logging)

        } catch (dive: DataIntegrityViolationException) {
            if (dive.message.orEmpty().contains("UK_")) {
                bindingResult.rejectValue("username", "username.alreadyInUse", "Username already in use.");
                user.password = ""
                return "register"
            } else {
                throw dive;
            }
        }
        return "redirect:/cloud?username=" + user.username
    }
}