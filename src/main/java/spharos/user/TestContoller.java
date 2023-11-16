package spharos.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class TestContoller {

    @GetMapping("/test")
    public String testMetmod(){
        return "User Service";
    }
    @GetMapping("/jenkins")
    public String jenkinsTestMethod(){
        return "This is Jenkins Test Method!";
    }
}
