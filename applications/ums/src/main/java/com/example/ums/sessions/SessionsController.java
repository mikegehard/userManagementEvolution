package com.example.ums.sessions;

import com.example.users.LogIn;
import com.example.users.Session;
import com.example.users.UaaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sessions")
public class SessionsController {

    @Autowired
    private UaaClient loginUserUaaClient;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Session> login(@RequestBody Map<String, String> params) {
        Optional<Session> session = new LogIn(loginUserUaaClient).run(params.get("name"), params.get("password"));

        return session.map(s -> new ResponseEntity<>(s, HttpStatus.CREATED)).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
