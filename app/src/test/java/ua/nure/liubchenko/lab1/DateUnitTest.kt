package ua.nure.liubchenko.lab1

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.text.SimpleDateFormat
import java.util.*

class DateUnitTest {

    @Test fun dateFormat_isCorrect() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val dateMillis = 1572379358000
        val dateFormatted = "29/10/2019"

        val res = sdf.format(Date(dateMillis))
        assertEquals(dateFormatted, res)
    }
}
