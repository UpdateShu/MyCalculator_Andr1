package com.example.mycalculatorandr1

import android.os.Bundle
import com.example.mycalculatorandr1.domain.Calculator
import com.example.mycalculatorandr1.domain.Operation
import com.example.mycalculatorandr1.domain.State
import com.example.mycalculatorandr1.ui.calc.CalculatorPresenter
import com.example.mycalculatorandr1.ui.calc.CalculatorView

import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.anyObject
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.math.BigDecimal

class CalculatorPresenterTests {

    private lateinit var presenter: CalculatorPresenter

    @Mock
    private lateinit var view: CalculatorView

    @Mock
    private lateinit var calculator: Calculator

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val maxEditLength = 10
        presenter = CalculatorPresenter(view, calculator, maxEditLength)
    }

    @Test
    fun onSaveState_test() {
        val bundle = mock(Bundle::class.java)
        presenter.onSaveState(bundle)

        verify(bundle).putParcelable(anyString(), anyObject())
    }

    @Test
    fun onRestoreState_test() {
        val bundle = mock(Bundle::class.java)
        val state = mock(State::class.java)
        `when`(bundle.getParcelable<State>(anyString())).thenReturn(state)

        val restoreArg = BigDecimal(5)
        `when`(state.currentArg).thenReturn(restoreArg)

        presenter.restoreState(bundle)
        Assert.assertEquals(presenter.currentArg, restoreArg)
    }

    @Test
    fun onBackPressed_OneDigit() {
        `when`(view.editValue).thenReturn("3")
        presenter.onBackPressed()

        Assert.assertNull(presenter.currentArg)
        verify(view, times(1)).editValue
    }

    @Test
    fun onBackPressed_MoreDigits() {
        `when`(view.editValue).thenReturn("123")
        presenter.onBackPressed()

        Assert.assertEquals(presenter.currentArg, BigDecimal(12))
    }

    @Test
    fun onDigitPressed_InputDigit() {
        val digit = 8
        presenter.onDigitPressed(digit)
        Assert.assertEquals(presenter.currentArg, BigDecimal(digit))
        Assert.assertEquals(presenter.editStr, digit.toString())
    }

    @Test
    fun onDigitPressed_InputMoreDigits() {
        presenter.onDigitPressed(1)
        Assert.assertEquals(presenter.currentArg, BigDecimal(1))

        presenter.onDigitPressed(2)
        Assert.assertEquals(presenter.currentArg, BigDecimal(12))

        presenter.onDigitPressed(3)
        Assert.assertEquals(presenter.currentArg, BigDecimal(123))
    }

    @Test
    fun onDotPressed_InputDec() {
        presenter.onDigitPressed(1)
        Assert.assertEquals(presenter.currentArg, BigDecimal(1))

        presenter.onDigitPressed(2)
        Assert.assertEquals(presenter.currentArg, BigDecimal(12))

        presenter.onDotPressed()
        presenter.onDigitPressed(3)
        Assert.assertTrue(presenter.currentArg.minus(BigDecimal(12.3)).abs() < BigDecimal(0.000000001))
    }

    @Test
    fun onOperandPressed_Plus() {
        val digit = 2
        presenter.onDigitPressed(2)
        presenter.onOperandPressed(Operation.PLUS)

        Assert.assertNull(presenter.currentArg)
        Assert.assertEquals(presenter.editStr, digit.toString())
    }

    @Test
    fun onOperandPressed_Equal() {
        val digit1 = 2
        presenter.onDigitPressed(digit1)
        presenter.onOperandPressed(Operation.PLUS)

        val digit2 = 3
        presenter.onDigitPressed(digit2)
        `when`(calculator.performOperation(digit1.toDouble(), digit2.toDouble(),
            Operation.PLUS)).thenReturn((digit1 + digit2).toDouble())

        presenter.onOperandPressed(Operation.EQUAL)
        Assert.assertEquals(presenter.editStr, (digit1 + digit2).toString())
    }
}

