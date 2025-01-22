package com.user.api.UsersRestApi.controller;

import com.user.api.UsersRestApi.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UserController {

    private static final List<UserDto> listUsers = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<UserDto>>getUserByName(){
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto>findById(@PathVariable Integer id){
        return new ResponseEntity<>(listUsers.get(id-1),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>>findByNameAndAge(@RequestParam(defaultValue = "Test1",name = "nombre") String name,
                                                         @RequestParam(required = false, name = "edad") Integer age){
        List<UserDto>filteredUsers=listUsers.stream()
                .filter(userDto -> userDto.getUserName().equalsIgnoreCase(name)
                && (age == null) || userDto.getAge().equals(age)).collect(Collectors.toList());

        return new ResponseEntity<>(filteredUsers,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user){
        listUsers.add(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id,
                                              @RequestBody UserDto user){
        UserDto userUpdated = listUsers.get(id-1);
        userUpdated.setUserName(user.getUserName());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setAge(user.getAge());
        return new ResponseEntity<>(userUpdated,HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Integer id){
        UserDto userDeleted = listUsers.get(id-1);
        listUsers.remove(id-1);
        return new ResponseEntity<>(userDeleted,HttpStatus.OK);
    }
}
