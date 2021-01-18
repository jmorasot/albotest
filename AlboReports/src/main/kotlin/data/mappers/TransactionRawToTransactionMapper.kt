package data.mappers

import com.beust.klaxon.Klaxon
import domain.models.RawTransaction
import domain.models.Transaction

class TransactionRawToTransactionMapper {

    private val dateMapper = DateStringToDateMapper()

    fun map(filePath: String) : List<Transaction> {
        val rawText = this::class
            .java
            .getResource(filePath)
            .readText()

        val rawList = Klaxon().parseArray<RawTransaction>(rawText) ?: listOf()

        return parseRawList(rawList)
    }

    private fun parseRawList(from: List<RawTransaction>): List<Transaction> {
        return from.map { parseRawContent(it) }
    }

    private fun parseRawContent(from: RawTransaction): Transaction {
        return Transaction(
            id = from.id,
            creationDate = dateMapper.map(from.creationDate),
            description = from.description,
            amount = from.amount,
            category = from.category,
            operation = from.operation,
            status = from.status
        )
    }
}