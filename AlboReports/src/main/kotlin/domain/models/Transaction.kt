package domain.models

import java.time.LocalDate

data class Transaction(
    val id: Long,
    val creationDate: LocalDate,
    val description: String,
    val amount: Double,
    val category: String,
    val operation: String,
    val status: String
)