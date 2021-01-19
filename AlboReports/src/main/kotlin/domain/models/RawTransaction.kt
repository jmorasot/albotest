package domain.models

import com.beust.klaxon.Json

data class RawTransaction(
    @Json(name = "uuid")
    val id: Long,
    @Json(name = "creation_date")
    val creationDate: String,
    val description: String,
    val amount: Double,
    val category: String,
    val operation: String,
    val status: String
)
