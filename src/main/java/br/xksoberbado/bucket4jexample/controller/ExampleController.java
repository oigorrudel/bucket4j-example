package br.xksoberbado.bucket4jexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class ExampleController {

    @GetMapping("1")
    public String get1() {
        return "Here 1!";
    }

    @GetMapping("2")
    public String get2() {
        return "Here 2!";
    }
}
