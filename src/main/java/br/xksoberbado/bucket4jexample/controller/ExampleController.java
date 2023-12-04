package br.xksoberbado.bucket4jexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("example")
public class ExampleController {

    @GetMapping("1")
    public String get1() {
        return "Opaaaa 1!";
    }

    @GetMapping("2")
    public String get2() {
        return "Opaaaa 2!";
    }

    @GetMapping("3")
    public String get3() {
        return "Opaaaa 3!";
    }
}
