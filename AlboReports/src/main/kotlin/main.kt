import data.mappers.TransactionRawToTransactionMapper
import domain.models.CategorySummary
import domain.models.Transaction
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.util.*

private const val STATUS_PENDING = "pending"
private const val STATUS_REJECTED = "rejected"
private const val STATUS_DONE = "done"

private const val OPERATION_IN = "in"
private const val OPERATION_OUT = "out"

private const val CURRENCY_PATTERN = "\$#,###,##0.00"

private val months = DateFormatSymbols().months

fun main(args: Array<String>) {
    val transactions = TransactionRawToTransactionMapper().map("/raw/transactions.json")

    buildFullReport(transactions)
}

private fun buildFullReport(transactions: List<Transaction>) {
    buildMonthReport(
        transactions = transactions,
        month = Calendar.JANUARY
    )

    buildMonthReport(
        transactions = transactions,
        month = Calendar.FEBRUARY
    )

    buildMonthReport(
        transactions = transactions,
        month = Calendar.MARCH
    )

    buildMonthReport(
        transactions = transactions,
        month = Calendar.MAY
    )

    buildMonthReport(
        transactions = transactions,
        month = Calendar.JUNE
    )

    buildMonthReport(
        transactions = transactions,
        month = Calendar.JULY
    )

    buildMonthReport(
        transactions = transactions,
        month = Calendar.AUGUST
    )

    buildMonthReport(
        transactions = transactions,
        month = Calendar.SEPTEMBER
    )

    buildMonthReport(
        transactions = transactions,
        month = Calendar.OCTOBER
    )

    buildMonthReport(
        transactions = transactions,
        month = Calendar.NOVEMBER
    )

    buildMonthReport(
        transactions = transactions,
        month = Calendar.DECEMBER
    )
}

private fun buildMonthReport(transactions: List<Transaction>, month: Int) {
    val monthTransactions = transactions.asSequence()
        .filter { it.creationDate.monthValue == (month + 1)}

    val pendingTransactions = monthTransactions.asSequence()
        .filter { it.status == STATUS_PENDING }
        .toList()

    val rejectedTransactions = monthTransactions.asSequence()
        .filter { it.status == STATUS_REJECTED }
        .toList()

    val income = monthTransactions.sumByDouble {
        if (it.operation == OPERATION_IN && it.status == STATUS_DONE) {
            it.amount
        } else {
            0.0
        }
    }

    val expenses = monthTransactions.sumByDouble {
        if (it.operation == OPERATION_OUT && it.status == STATUS_DONE) {
            it.amount
        } else {
            0.0
        }
    }

    val totalExpenses = monthTransactions.sumByDouble {
        if (it.operation == OPERATION_OUT) {
            it.amount
        } else {
            0.0
        }
    }

    val detailList = mutableListOf<CategorySummary>()
    monthTransactions.filter { it.operation == OPERATION_OUT }
        .groupBy { transaction ->
            transaction.category
        }.forEach { (key, value) ->
            val totalByCategory = value.sumByDouble {
                it.amount
            }

            val percentage: Double = (totalByCategory / totalExpenses) * 100.0
            detailList.add(CategorySummary(key, percentage))
        }


    println(getMonthName(month))
    println("\t ${pendingTransactions.size} transacciones pendientes")
    println("\t ${rejectedTransactions.size} transacciones bloquadas")
    println()
    println("\t ${parseAmountAsCurrency(income)} en ingresos")
    println("\t ${parseAmountAsCurrency(expenses)} en gastos")
    println()
    println("Detalle de gastos")
    detailList.sortByDescending { it.percentage }
    detailList.forEach { summary ->
        println("\t ${summary.name} \t ${parseAmountAsCurrency(summary.percentage).replace("$", "")}%")
    }
    println()
}

private fun parseAmountAsCurrency(amount: Double): String {
    return DecimalFormat(CURRENCY_PATTERN).format(amount)
}

private fun getMonthName(month: Int): String {
    return months[month]
}