package com.user.api.UsersRestApi.service;

import com.user.api.UsersRestApi.dto.UserDto;
import com.user.api.UsersRestApi.exceptions.NotFoundException;
import com.user.api.UsersRestApi.model.User;
import com.user.api.UsersRestApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User>listAllUsers(int page,int size,String direction,String sort){
        Sort sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.by(Sort.Order.desc(sort))
                : Sort.by(Sort.Order.asc(sort));

        return userRepository.findAll(PageRequest.of(page,size,sortDirection));
    }

    public Optional<User> findById(Integer id){
        if(id == null || id < 1){
            throw new IllegalArgumentException("Wrong param value should not be null or should be grater than 0");
        }
        Optional<User>user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new NotFoundException("")));
        return user;
    }

    public List<User>listByName(String name){

        return userRepository.findByUserName(name);
    }

    public List<User>findByUserNameAndAge(String name,Integer age){

        return userRepository.findByUserNameAndAge(name,age);
    }
    public void createUser(User user) {
        userRepository.save(user);
    }

    public User updateUser(Integer id, User user) {
        User userUpdated = userRepository.findById(id).orElseThrow(()->new NotFoundException(""));
        if(user.getUserName() != null && !user.getUserName().isEmpty()){
            userUpdated.setUserName(user.getUserName());
        }
        if(user.getEmail() != null && !user.getEmail().isEmpty()){
            userUpdated.setEmail(user.getEmail());
        }
        if(user.getAge()!= null && user.getAge() > 0){
            userUpdated.setAge(user.getAge());
        }
        userRepository.save(userUpdated);
        return userUpdated;
    }

    public void deleteUser(Integer id){
        User userDeleted = userRepository.findById(id).orElseThrow(()->new NotFoundException(""));
        userRepository.delete(userDeleted);
    }

}
