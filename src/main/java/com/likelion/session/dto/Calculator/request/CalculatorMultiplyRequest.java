package com.likelion.session.dto.Calculator.request;

public class CalculatorMultiplyRequest
{
    private final int number1;
    private final int number2;

    public CalculatorMultiplyRequest(int number1, int number2){
        this.number1 = number1;
        this.number2 = number2;
    }

    public CalculatorMultiplyRequest(){
        this.number1=0;
        this.number2=0;
    }

    public int getNumber1(){
        return number1;
    }

    public int getNumber2(){
        return number2;
    }

}
