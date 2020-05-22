package at.fhj.ima.swengaprojekt.cloud.controller

import at.fhj.ima.swengaprojekt.cloud.dto.CloudUserDto
import at.fhj.ima.swengaprojekt.cloud.service.DepartmentService
import at.fhj.ima.swengaprojekt.cloud.service.CloudUserService
import at.fhj.ima.swengaprojekt.cloud.service.ProjectService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@Controller
class CloudUserController(val departmentService: DepartmentService,
                          val projectService: ProjectService,
                          val cloudUserService: CloudUserService
) {

    fun showEditCloudUserView(model: Model): String {
        model.set("departments", departmentService.findAll())
        model.set("projects", projectService.findAll())
        return "editCloudUser"
    }

    @RequestMapping("/editCloudUser", method = [RequestMethod.GET])
    @Secured("ROLE_ADMIN")
    fun editCloudUser(model: Model, @RequestParam(required = false) ssn: Int?): String {
        if (ssn != null) {
            model.set("cloudUser", cloudUserService.findBySsn(ssn))
        } else {
            model.set("cloudUser", cloudUserService.createNewCloudUser())
        }
        return showEditCloudUserView(model)
    }

    @RequestMapping("/createCloudUser", method = [RequestMethod.GET])
    fun createCloudUser(model: Model, @RequestParam(required = false) ssn: Int?): String {
        if (ssn != null) {
            model.set("cloudUser", cloudUserService.findBySsn(ssn))
        } else {
            model.set("cloudUser", cloudUserService.createNewCloudUser())
        }
        return showEditCloudUserView(model)
    }

    @RequestMapping("/changeCloudUser", method = [RequestMethod.POST])
    @Secured("ROLE_ADMIN")
    fun changeCloudUser(@ModelAttribute("cloudUser") @Valid cloudUser: CloudUserDto, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return showEditCloudUserView(model)
        }
        try {
            cloudUserService.save(cloudUser)
        } catch (dive: DataIntegrityViolationException) {
            if (dive.message.orEmpty().contains("ssn_UK")) {
                bindingResult.rejectValue("ssn", "ssn.alreadyInUse", "SSN already in use.");
                return showEditCloudUserView(model)
            } else {
                throw dive;
            }
        }

        return "redirect:/editCloudUser?ssn=" + cloudUser.ssn
    }

    @RequestMapping("/listCloudUsers", method = [RequestMethod.GET])
    fun listCloudUsers(model: Model, @RequestParam(required = false) search: String?): String {
        if (!search.isNullOrBlank()) {
            model.set("cloudUsers", cloudUserService.findByFirstnameOrLastname(search))
        } else {
            model.set("cloudUsers", cloudUserService.findAll())
        }
        return "listCloudUsers"
    }

    @RequestMapping("/deleteCloudUser", method = [RequestMethod.POST])
    @Secured("ROLE_ADMIN")
    fun deleteCloudUser(model: Model, @RequestParam ssn: Int): String {
        cloudUserService.delete(ssn);
        model.set("message", "CloudUser $ssn deleted")
        return listCloudUsers(model, null)
    }

    @ExceptionHandler(Exception::class)
    fun handleError(req: HttpServletRequest, ex: Exception): ModelAndView {
        val mav = ModelAndView()
        mav.addObject("exception", ex)
        mav.addObject("url", req.requestURI)
        mav.viewName = "error"
        return mav
    }

}
