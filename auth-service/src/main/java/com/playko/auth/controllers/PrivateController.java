package com.playko.auth.controllers;

import com.playko.auth.dtos.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivateController {

    @GetMapping("/messages")
    public ResponseEntity<MessageDto> privateMessages(@AuthenticationPrincipal(expression = "name") String name) {
        return ResponseEntity.ok(new MessageDto("private content " + name));
    }
}