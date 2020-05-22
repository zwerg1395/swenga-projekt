package at.fhj.ima.employee.employeemanager.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class AnonymousController {

    @RequestMapping("/anonymous", method = [RequestMethod.GET])
    fun anonymous(): String {
        return "anonymous"
    }
}
