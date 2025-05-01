package com.likelion.session.Controller.Calculator;

import com.likelion.session.dto.Calculator.request.CalculatorAddRequest;
import com.likelion.session.dto.Calculator.request.CalculatorMultiplyRequest;
import com.likelion.session.service.Calculator.CalculatorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }

    //get으로 데이터 받아옴
    @GetMapping("/add")
    //dto 사용
    /*public int addTwoNumbers(@RequestBody CalculatorAddRequest request){
        return request.getNumber1() + request.getNumber2();
    }*/
    //서비스 사용
    public int addTwoNumbers(@RequestBody CalculatorAddRequest request){
        return calculatorService.add(request.getNumber1(), request.getNumber2());
    }
    //파라미터로 바로 데이터 받아옴
    /*public int addTwoNumbers(
            @RequestParam(name = "number1") int number1,
            @RequestParam(name = "number2") int number2
    ){
        return number1 + number2;
    }*/

    //post로 데이터 받아옴
    @PostMapping("/multiply")
    //dto 사용
    /*public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request){
        return request.getNumber1() * request.getNumber2();
    }*/
    //서비스 사용
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request){
        return calculatorService.multiply(request.getNumber1(), request.getNumber2());
    }


}
