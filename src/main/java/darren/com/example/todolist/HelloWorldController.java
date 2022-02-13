package darren.com.example.todolist;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
public class HelloWorldController {
        @RequestMapping("/")
    public String syHello(){

            return "123";
    }
}
