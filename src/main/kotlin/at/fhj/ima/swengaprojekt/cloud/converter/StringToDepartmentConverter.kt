package at.fhj.ima.swengaprojekt.cloud.converter

import at.fhj.ima.swengaprojekt.cloud.entity.Department
import at.fhj.ima.swengaprojekt.cloud.repository.DepartmentRepository
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class StringToDepartmentConverter(val departmentRepository: DepartmentRepository) : Converter<String, Department?> {
    override fun convert(source: String): Department? {
        if (source.isNullOrBlank()) {
            return null
        }
        return departmentRepository.getOne(Integer.parseInt(source));
    }
}
