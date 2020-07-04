package com.myretail.api.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MyRetail API redirection to swagger api documentation
 */
@RestController
public class HomeController {

    @RequestMapping(value = "/")
    public String index() {
        System.out.println("swagger-ui.html");
        return "redirect:swagger-ui.html";
    }
}
