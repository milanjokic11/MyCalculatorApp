package eu.tutorials.mycalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDecimal: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // assign corresponding ID's
        tvInput = findViewById(R.id.tvInput)
    }

    // appends digit to calc. console when pressed
    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        // flags
        lastNumeric = true
        lastDecimal = false
    }

    // clears calculator console
    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDecimal) {
            tvInput?.append(".")
            // flags
            lastNumeric = false
            lastDecimal = true
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                // flags
                lastNumeric = false
                lastDecimal = false
            }
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                // if subtracting from negative no.
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                // subtraction
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var a = splitValue[0]
                    var b = splitValue[1]
                    if (prefix.isNotEmpty())
                        a = prefix + a
                    tvInput?.text = removeZeroAfterDecimal((a.toDouble() - b.toDouble()).toString())
                }
                // addition
                else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var a = splitValue[0]
                    var b = splitValue[1]
                    if (prefix.isNotEmpty())
                        a = prefix + a
                    tvInput?.text = removeZeroAfterDecimal((a.toDouble() + b.toDouble()).toString())
                }
                // multiplication
                else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var a = splitValue[0]
                    var b = splitValue[1]
                    if (prefix.isNotEmpty())
                        a = prefix + a
                    tvInput?.text = removeZeroAfterDecimal((a.toDouble() * b.toDouble()).toString())
                }
                // division
                else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var a = splitValue[0]
                    var b = splitValue[1]
                    if (prefix.isNotEmpty())
                        a = prefix + a
                    tvInput?.text = removeZeroAfterDecimal((a.toDouble() / b.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    // function to remove non-essential decimal point
    private fun removeZeroAfterDecimal(result: String): String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    // function to check if a arithmetic operator is added
    private fun isOperatorAdded(value: String): Boolean {
        // allows negative numbers to be subtracted
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}