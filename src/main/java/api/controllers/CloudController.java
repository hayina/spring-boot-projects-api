package api.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/cloud")
public class CloudController {

    @Value("${yous:config server is DOWN!!}")
    private String yous;

    @GetMapping
    public String getYous() {
        return yous;
    }
}
