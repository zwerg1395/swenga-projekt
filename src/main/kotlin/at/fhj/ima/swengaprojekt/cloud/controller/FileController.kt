package at.fhj.ima.swengaprojekt.cloud.controller

import at.fhj.ima.swengaprojekt.cloud.dto.LoggingDto
import at.fhj.ima.swengaprojekt.cloud.entity.File
import at.fhj.ima.swengaprojekt.cloud.entity.UserDto
import at.fhj.ima.swengaprojekt.cloud.entity.UserRole
import at.fhj.ima.swengaprojekt.cloud.service.FileService
import at.fhj.ima.swengaprojekt.cloud.service.LoggingService
import at.fhj.ima.swengaprojekt.cloud.service.UserService
import org.springframework.core.io.FileSystemResource
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@Controller
class FileController(val fileService: FileService, val userService: UserService, val loggingService: LoggingService) {

    @RequestMapping("/file/{id}", method = [RequestMethod.GET])
    fun downloadFile(@PathVariable("id") id: Int, logging: LoggingDto): ResponseEntity<Any> {
        val file = fileService.findById(id)
        val path = fileService.retrievePath(id)
        val fileSystemResource = FileSystemResource(path)


        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val currentPrincipalName: String = authentication.getName()

        var files = userService.findByUsername(currentPrincipalName).files

        if (files!!.contains(File(id))){

            logging.user = currentPrincipalName
            logging.action = "File with id " + File(id).id + " and name " + fileService.findById(id).originalFileName + " was downloaded."
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            logging.date = formatted.toString()
            loggingService.save(logging)

            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + file.originalFileName)
                .contentType(MediaType.parseMediaType(file.contentType!!))
                .body(fileSystemResource)
        }

        return ResponseEntity.ok("No Permission!");
    }

    @RequestMapping("/file", method = [RequestMethod.POST])
    @ResponseBody
    fun uploadFile(@RequestParam("file") file: MultipartFile): File {
        return fileService.createFile(file)
    }

    @RequestMapping("/file/delete/{id}", method = [RequestMethod.GET])
    fun deleteFile(@PathVariable("id") id: Int, logging: LoggingDto, request: HttpServletRequest): String {

        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val currentPrincipalName: String = authentication.getName()

        var files = userService.findByUsername(currentPrincipalName).files

        if (files!!.contains(File(id))){

            logging.user = currentPrincipalName
            logging.action = "File with id " + File(id).id + " and name " + fileService.findById(id).originalFileName + " was deleted."
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            logging.date = formatted.toString()

            loggingService.save(logging)

            fileService.delete(id)
        }

        val referer = request.getHeader("Referer")
        return "redirect:$referer"
    }

    @RequestMapping("/file/rename/{id}", method = [RequestMethod.GET])
    fun rename(@PathVariable("id") id: Int, @RequestParam(required = true) fileName: String, logging: LoggingDto, request: HttpServletRequest): String {

        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val currentPrincipalName: String = authentication.getName()

        var files = userService.findByUsername(currentPrincipalName).files
        var file:File = fileService.findById(id)

        if (files!!.contains(File(id))){
            logging.user = currentPrincipalName
            logging.action = "File with id " + File(id).id + " was renamed from " + file.originalFileName + " to " + fileName + "."
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            logging.date = formatted.toString()

            loggingService.save(logging)

            fileService.rename(file, fileName)
        }

        val referer = request.getHeader("Referer")
        return "redirect:$referer"
    }

    @ExceptionHandler(Exception::class)
    fun handleError(req: HttpServletRequest, ex: Exception): ModelAndView {
        val mav = ModelAndView()
        mav.addObject("exception", ex)
        mav.addObject("url", req.requestURI)
        mav.viewName = "error"
        return mav
    }

    fun deleteFileWithModel(@ModelAttribute("file") @Valid file: File) {

        fileService.delete(file.id!!)
    }
}
