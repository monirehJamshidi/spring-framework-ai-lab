package org.j2os.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonAPI {

    @Value("${behsazan.development4.project}")
    private String projectName;

    @GetMapping("/test")
    public String test(){
        System.out.println("test");
        return projectName;
    }

    @GetMapping("/1")
    public String execute1(){
        return "ONE";
    }

    @PostMapping("/2")
    public String execute2(){
        return "TWO";
    }
}
