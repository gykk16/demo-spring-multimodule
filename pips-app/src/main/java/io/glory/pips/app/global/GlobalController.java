package io.glory.pips.app.global;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

import io.glory.coreweb.aop.security.SecuredIp;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pips/global")
@RequiredArgsConstructor
public class GlobalController {

    private final Environment env;

    @GetMapping("/health")
    @SecuredIp
    public String health() {
        return "UP";
    }

    @GetMapping("/profile")
    @SecuredIp
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .filter(profile -> profile.contains("set"))
                .findFirst()
                .orElse("");
    }

}
