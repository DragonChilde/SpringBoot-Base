package Application.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lee
 * @create 2020/3/27 11:46
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello()
    {
      return  "Hello world";
    }
}
