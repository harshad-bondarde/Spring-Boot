package com.example.myFirstProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Cat {

    @Autowired //depedendcy injection now Cat is depended on Dog
    private Dog dog;
    // all classes that would need Dog object will be provided with this same Dog class

    @GetMapping("ok")
    public String ok(){
        return dog.fun();
    }
}
