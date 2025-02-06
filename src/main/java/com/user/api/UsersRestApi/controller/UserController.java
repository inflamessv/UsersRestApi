package com.user.api.UsersRestApi.controller;

import com.user.api.UsersRestApi.dto.UserDto;
import com.user.api.UsersRestApi.model.User;
import com.user.api.UsersRestApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

   @Autowired
   private UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>>getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "userName") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ){
        return ResponseEntity.ok().body(userService.listAllUsers(page, size, direction, sort));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<User>findById(@PathVariable Integer id){
        Optional<User> user = userService.findById(id);
        return ResponseEntity.ok().body(user.get());
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<List<User>>findByName(@PathVariable String name){
        return ResponseEntity.ok().body(userService.listByName(name));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<User>>findByNameAndAge(@RequestParam(defaultValue = "Test1",name = "userName") String userName,
                                                         @RequestParam(required = false, name = "age") Integer age){
        List<User>filteredUsers=userService.findByUserNameAndAge(userName,age);
        return ResponseEntity.ok(filteredUsers);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id,
                                              @RequestBody User user){
        User userUpdated = userService.updateUser(id,user);
        return  ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User has been deleted");
    }















}
