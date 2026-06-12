package com.example.myFirstProject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class myClass {
    //FROM now on we wont be creating the objects we will ask spring boot to create and give us objects
    // we will get objects from ioc container / Application context
//    @Component if we write this annotation before the class ioc will add this in it and this would be registered as spring bean
//    public String myclass{
//
//    }

    @GetMapping("abc")
    public String sayHello(){
        return "Hello";
    }
}
