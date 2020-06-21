package at.fhj.ima.swengaprojekt.cloud.controller

import at.fhj.ima.swengaprojekt.cloud.repository.LoggingRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.time.Instant
import javax.servlet.http.HttpServletRequest


@Controller
class HomePageController(val loggingRepository: LoggingRepository) {

    @RequestMapping("", method = [RequestMethod.GET])
    fun homePage(): String {
        return "redirect:home"
    }

    @RequestMapping("/home", method = [RequestMethod.GET])
    fun home(): String {

        return "home"
    }

    @RequestMapping("/refresh", method = [RequestMethod.GET])
    fun refresh(request: HttpServletRequest): String {

        val referer = request.getHeader("Referer")
        return "redirect:$referer"
    }

    /*
    @RequestMapping("/getLog", method = [RequestMethod.GET], produces = [MediaType.APPLICATION_PDF_VALUE])
    @Secured("ROLE_ADMIN")
    fun createLog(request: HttpServletRequest, response: HttpServletResponse): String {

        System.out.println(loggingRepository.findAll())

        val referer = request.getHeader("Referer")
        return loggingRepository.findAll()
    }

     */
}
