package com.example.hw3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var operators: ArrayList<Button>;
    private lateinit var numbers: ArrayList<Button>;
    private lateinit var expression: ArrayList<String>;
    private lateinit var equal: Button;
    private lateinit var allClear: Button;
    private lateinit var minus: Button;
    private lateinit var flipSign: Button;
    private lateinit var res: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operators = ArrayList();
        operators.add(findViewById(R.id.add))
        operators.add(findViewById(R.id.multiply))
        operators.add(findViewById(R.id.divide))
        operators.add(findViewById(R.id.modal))
        operators.forEach(::addOperator)

        minus = findViewById(R.id.subtract);
        minus.setOnClickListener { addMinus() }
        flipSign = findViewById(R.id.switch_sign)
        flipSign.setOnClickListener { switchSign() }


        numbers = ArrayList()
        numbers.add(findViewById(R.id.nine))
        numbers.add(findViewById(R.id.eight))
        numbers.add(findViewById(R.id.seven))
        numbers.add(findViewById(R.id.six))
        numbers.add(findViewById(R.id.five))
        numbers.add(findViewById(R.id.four))
        numbers.add(findViewById(R.id.three))
        numbers.add(findViewById(R.id.two))
        numbers.add(findViewById(R.id.one))
        numbers.add(findViewById(R.id.zero))
        numbers.add(findViewById(R.id.point))
        numbers.forEach(::addDigit)

        allClear = findViewById(R.id.all_clear)
        allClear.setOnClickListener {
            res.text ="0"
            expression = ArrayList();
        }
        equal = findViewById(R.id.equal)
        equal.setOnClickListener { evaluate() }

        res = findViewById(R.id.result)
        expression = ArrayList();
    }

    fun addDigit(button: Button) {
        button.setOnClickListener{
            val digit = button.text.toString();
            if(expression.size==0){
                expression.add(digit);
            }else {
                var lastItem = expression.removeLast()
                lastItem += digit;
                Log.d("lastItem", lastItem)
                expression.add(lastItem);
            }
            setResult();
        }
    }

    fun addMinus () {
        if (expression.size == 0) {
            expression.add("-")
        } else {
            expression.removeLast();
            expression.add("-");
        }
        setResult();
    }

    fun switchSign () {
        if (expression.size == 0) {
            expression.add("-")
        } else {
            var lastItem =expression.removeLast();
            expression.add("-$lastItem");
        }
        setResult();
    }

    fun addOperator(button: Button) {
        button.setOnClickListener{
            val operator = button.text.toString();
            if(expression.size == 0 || expression.last() == ""){
                if (operator == "−" ){
                    if(expression.size ==0){
                        expression.add("-")
                    }else{
                        expression.removeLast();
                        expression.add("-");
                    }
                }else {
                    setResultError();
                }
            }else {
                expression.add(operator)
                expression.add("")
                setResult();
            }
        }
    }

    fun expressionToString(): String{
        return expression.joinToString (separator = " ") {it}
    }

    fun evaluateOperator(num1: Float, operator:String, num2:Float): Float {
        when (operator) {
            "%"-> return num1%num2;
            "÷" -> return num1/num2;
            "×" -> return num1*num2;
            "−" -> return num1-num2;
            "+" -> return num1+num2;
            else -> {
                throw Throwable("Incorrect Operator")
            }
        }
    }

    fun evaluate() {
        if (expression.size<3 || expression.size%2 ==0 ) {
            setResultError();
        }else {
            try {
                var result = 0f;
                var i = 1;
                var prev: Float = expression[0].toFloat()
                var operator: String;
                var next: Float;
                while (i < expression.size) {
                    operator = expression[i];
                    next = expression[i + 1].toFloat();

                    result = evaluateOperator(prev, operator, next);
                    Log.d("result", result.toString())
                    prev = result

                    i += 2;

                }
                res.text = result.toString();
                expression = arrayListOf(result.toString());
            }catch (throwable: Throwable){
                setResultError();
            }
        }
    }

    fun setResultError(){
        res.text = "Error";
        expression=ArrayList();
    }

    fun setResult(){
        res.text = expressionToString();
    }
}