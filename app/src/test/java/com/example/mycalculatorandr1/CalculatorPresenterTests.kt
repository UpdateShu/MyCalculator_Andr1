package com.example.mycalculatorandr1

import android.os.Bundle
import com.example.mycalculatorandr1.domain.Calculator
import com.example.mycalculatorandr1.domain.State
import com.example.mycalculatorandr1.ui.calc.CalculatorPresenter
import com.example.mycalculatorandr1.ui.calc.CalculatorView
import com.example.mycalculatorandr1.ui.calc.EditValuePresenter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.anyObject
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
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
    fun onBackPressed_Test() {

        `when`(view.editValue).thenReturn("12")

        presenter.onBackPressed()
    }

    @Test
    fun onDigitPressed_StartInput() {
        Assert.assertEquals(presenter.onDigitPressed(8), BigDecimal(8))
    }

    @Test
    fun onDigitPressed_MoreDigits() {
        Assert.assertEquals(presenter.onDigitPressed(1), BigDecimal(1))
        Assert.assertEquals(presenter.onDigitPressed(2), BigDecimal(12))
        Assert.assertEquals(presenter.onDigitPressed(3), BigDecimal(123))
    }

    @Test
    fun onDotPressed_Dec() {
        Assert.assertEquals(presenter.onDigitPressed(1), BigDecimal(1))
        Assert.assertEquals(presenter.onDigitPressed(2), BigDecimal(12))
        presenter.onDotPressed()

        Assert.assertTrue(presenter.onDigitPressed(3).minus(BigDecimal(12.3)).abs() < BigDecimal(0.000000001))
    }
}

