package com.alayon.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private val substractOperator = "-"
    private val multiplicationOperator = "X"
    private val plusOperator = "+"
    private val divideOperator = "/"

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view:View){
        txtInput.append((view as Button).text)
        lastNumeric = true
        txtInput.text.contains("1")
    }

    fun onClear(view:View){
        txtInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view:View){
        if (lastNumeric && !lastDot){
            txtInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view:View){
        if (lastNumeric && !isOperatorAdded(txtInput.text.toString())){
            txtInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEqual(view:View){
        if(lastNumeric){
            var txtValue = txtInput.text.toString()
            var prefix = ""
            try{
                if (txtValue.startsWith(substractOperator)){
                    prefix = substractOperator
                    txtValue = txtValue.substring(1)
                }
                if (txtValue.contains(substractOperator)){
                    val splitValue = txtValue.split(substractOperator)
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    txtInput.text = removeCeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if (txtValue.contains(plusOperator)){
                    val splitValue = txtValue.split(plusOperator)
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    txtInput.text = removeCeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }else if (txtValue.contains(multiplicationOperator)){
                    val splitValue = txtValue.split(multiplicationOperator)
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    txtInput.text = removeCeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (txtValue.contains(divideOperator)){
                    val splitValue = txtValue.split(divideOperator)
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    txtInput.text = removeCeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("X")
                    || value.contains("+") || value.contains("_")
        }
    }

    private fun removeCeroAfterDot(result:String):String{
        return if(!result.contains(".0"))
                result
        else
             result.substring(0, result.length-2)
    }
}