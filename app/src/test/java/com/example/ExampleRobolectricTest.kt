package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.PaymentMethod
import com.example.model.TransactionFormState
import com.example.model.TransactionType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches SpendWise app name`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("SpendWise", appName)
    }

    @Test
    fun `form validation rejects zero amount and blank title`() {
        val invalidState = TransactionFormState(
            title = "",
            amount = "0.00",
            type = TransactionType.EXPENSE
        ).validate()

        assertFalse(invalidState.isValid)
        assertTrue(invalidState.isRejected)
        assertNotNull(invalidState.titleError)
        assertNotNull(invalidState.amountError)
        assertNotNull(invalidState.rejectedReason)
    }

    @Test
    fun `form validation rejects negative amount`() {
        val invalidState = TransactionFormState(
            title = "Coffee",
            amount = "-5.50",
            type = TransactionType.EXPENSE
        ).validate()

        assertFalse(invalidState.isValid)
        assertTrue(invalidState.isRejected)
        assertNotNull(invalidState.amountError)
    }

    @Test
    fun `form validation accepts valid transaction data`() {
        val validState = TransactionFormState(
            title = "Groceries",
            amount = "42.50",
            type = TransactionType.EXPENSE,
            categoryId = "food",
            paymentMethod = PaymentMethod.CREDIT_CARD
        ).validate()

        assertTrue(validState.isValid)
        assertFalse(validState.isRejected)
        assertNull(validState.titleError)
        assertNull(validState.amountError)
    }
}
