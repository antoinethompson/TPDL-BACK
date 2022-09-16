package com.tpdl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/callback")
public class TestRest {
    public ResponseEntity<String> ok(@RequestParam("code") String authcode){
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

}
