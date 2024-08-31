package org.example.restapi.product;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/DontSeeThis")
public class LOL {

    @GetMapping
    public String OMG() {
        return "<strong style=\"color:red\">I HATE MY LIFE</strong>";
    }
}
