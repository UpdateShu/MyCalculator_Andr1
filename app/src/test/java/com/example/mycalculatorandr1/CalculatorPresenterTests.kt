package com.example.mycalculatorandr1

import com.example.mycalculatorandr1.domain.Calculator
import com.example.mycalculatorandr1.ui.calc.CalculatorPresenter
import com.example.mycalculatorandr1.ui.calc.CalculatorView
import com.example.mycalculatorandr1.ui.calc.EditValuePresenter
import org.junit.Assert

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CalculatorPresenterTests {

    private lateinit var presenter: CalculatorPresenter

    @Mock
    private lateinit var view: CalculatorView

    @Mock
    private lateinit var calculator: Calculator

    /*@Mock
    private var prevArg: BigDecimal? = null

    @Mock
    private var currentArg = BigDecimal(0)

    @Mock
    private val prevOperation: Operation? = null*/

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val maxEditLength = 10
        presenter = CalculatorPresenter(view, calculator, maxEditLength)
    }

    @Test
    fun test() {

        Assert.assertTrue(presenter.test())

        /*val editor = mock(EditValuePresenter::class.java)
        `when` (presenter.editPresenter).thenReturn(editor)

        presenter.onDotPressed()
        verify(editor).reset()*/
    }
}

