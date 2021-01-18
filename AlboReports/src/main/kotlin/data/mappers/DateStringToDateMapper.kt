package data.mappers

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateStringToDateMapper {

    private val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

    fun map(stringDate: String): LocalDate {
        return try {
            LocalDate.parse(stringDate, formatter)
        } catch (exception: Exception) {
            print("Date parsing error")
            LocalDate.now()
        }
    }
}
