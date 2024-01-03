package io.glory.pips.app.global;

import io.glory.coreweb.aop.security.SecuredIp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pips")
public class HomeController {

    @Value("${info.app.version:}")
    private String appVersion;

    @GetMapping
    @SecuredIp
    public String home() {
        return "Privacy information processing system App, Version: " + appVersion;
    }

}
