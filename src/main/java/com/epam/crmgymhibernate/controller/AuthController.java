package com.epam.crmgymhibernate.controller;

import com.epam.crmgymhibernate.dto.request.ChangePasswordRequest;
import com.epam.crmgymhibernate.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/{name}")
    public String check(@PathVariable String name) {
        return "Hello!!! " + name + " How are You?";
    }

    @PutMapping
    public void changePassword(@Valid  @RequestBody ChangePasswordRequest request) {
        authService.changeUserPassword(request);
    }

}
