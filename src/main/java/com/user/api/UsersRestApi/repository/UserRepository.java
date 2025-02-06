package com.user.api.UsersRestApi.repository;

import com.user.api.UsersRestApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User>findByUserName(String userName);
    List<User>findByUserNameAndAge(String userName,Integer age);
}
