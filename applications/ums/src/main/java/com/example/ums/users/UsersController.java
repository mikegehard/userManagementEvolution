package com.example.ums.users;

import com.example.users.CreateUser;
import com.example.users.UaaClient;
import com.example.users.User;
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
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UaaClient createUserUaaClient;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody Map<String, String> params) {
        Optional<User> user = new CreateUser(createUserUaaClient).run(params.get("name"), params.get("password"));

        return user.map(u -> new ResponseEntity<>(u, HttpStatus.CREATED)).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
