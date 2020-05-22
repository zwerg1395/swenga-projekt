package at.fhj.ima.swengaprojekt.cloud.controller

import at.fhj.ima.swengaprojekt.cloud.dto.EmployeeDto
import at.fhj.ima.swengaprojekt.cloud.service.DepartmentService
import at.fhj.ima.swengaprojekt.cloud.service.EmployeeService
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
class EmployeeController(val departmentService: DepartmentService,
                         val projectService: ProjectService,
                         val employeeService: EmployeeService
) {

    fun showEditEmployeeView(model: Model): String {
        model.set("departments", departmentService.findAll())
        model.set("projects", projectService.findAll())
        return "editEmployee"
    }

    @RequestMapping("/editEmployee", method = [RequestMethod.GET])
    @Secured("ROLE_ADMIN")
    fun editEmployee(model: Model, @RequestParam(required = false) ssn: Int?): String {
        if (ssn != null) {
            model.set("employee", employeeService.findBySsn(ssn))
        } else {
            model.set("employee", employeeService.createNewEmployee())
        }
        return showEditEmployeeView(model)
    }

    @RequestMapping("/changeEmployee", method = [RequestMethod.POST])
    @Secured("ROLE_ADMIN")
    fun changeEmployee(@ModelAttribute("employee") @Valid employee: EmployeeDto, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return showEditEmployeeView(model)
        }
        try {
            employeeService.save(employee)
        } catch (dive: DataIntegrityViolationException) {
            if (dive.message.orEmpty().contains("ssn_UK")) {
                bindingResult.rejectValue("ssn", "ssn.alreadyInUse", "SSN already in use.");
                return showEditEmployeeView(model)
            } else {
                throw dive;
            }
        }

        return "redirect:/editEmployee?ssn=" + employee.ssn
    }

    @RequestMapping("/listEmployees", method = [RequestMethod.GET])
    fun listEmployees(model: Model, @RequestParam(required = false) search: String?): String {
        if (!search.isNullOrBlank()) {
            model.set("employees", employeeService.findByFirstnameOrLastname(search))
        } else {
            model.set("employees", employeeService.findAll())
        }
        return "listEmployees"
    }

    @RequestMapping("/deleteEmployee", method = [RequestMethod.POST])
    @Secured("ROLE_ADMIN")
    fun deleteEmployee(model: Model, @RequestParam ssn: Int): String {
        employeeService.delete(ssn);
        model.set("message", "Employee $ssn deleted")
        return listEmployees(model, null)
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
