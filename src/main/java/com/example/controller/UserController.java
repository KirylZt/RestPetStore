package com.example.controller;

import com.example.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final List<User> userList = new ArrayList<>();

    @PostMapping
    public void save(@RequestBody User user){
        userList.add(user);
    }

    @PutMapping("/update}")
    public void update(@RequestBody User user){
        userList.set(userList.indexOf(user), user);
    }

    @PutMapping("/delete/{userName}")
    public void deleteUser(@PathVariable String userName) {
        userList.removeIf(p -> p.getUserName() == userName);
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String userName){
        for (User user: userList){
            if (user.getUserName().equals(userName)){
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
