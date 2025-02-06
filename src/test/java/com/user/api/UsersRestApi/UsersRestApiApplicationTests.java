package com.user.api.UsersRestApi;

import com.user.api.UsersRestApi.exceptions.NotFoundException;
import com.user.api.UsersRestApi.model.User;
import com.user.api.UsersRestApi.repository.UserRepository;
import com.user.api.UsersRestApi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UsersRestApiApplicationTests {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private User user;
	@BeforeEach
	public void setUp(){
		user=new User();
		user.setId(1);
		user.setUserName("test_user");
		user.setEmail("testUser@mail.com");
		user.setAge(18);
	}

	@Test
	public void testFindUserById(){
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		Optional<User>foundUser= userService.findById(user.getId());
		assertTrue(foundUser.isPresent());
		assertEquals("test_user",foundUser.get().getUserName());
	}

	@Test
	public void testFundUserByIdError(){
		when(userRepository.findById(5)).thenThrow(NotFoundException.class);
		when(userRepository.findById(5)).thenReturn(Optional.empty());
		User userFound =userService.findById(5).get();
		assertNull(userFound);
		assertThrows(NotFoundException.class,()->userService.findById(5));
	}
	@Test
	public void testFindByName(){
		when(userRepository.findByUserName(user.getUserName())).thenReturn(List.of(user));
		List<User>lstUserMock=userService.listByName(user.getUserName());
		assertEquals("test_user",lstUserMock.get(0).getUserName());
		verify(userRepository,times(1)).findByUserName(user.getUserName());
	}
	@Test
	public void testFindUserByNameAndAge(){
		when(userRepository.findByUserNameAndAge(user.getUserName(),user.getAge()))
				.thenReturn(List.of(user));
		List<User>userFound = userService.findByUserNameAndAge(user.getUserName(),user.getAge());
		assertEquals("test_user",userFound.get(0).getUserName());
		assertEquals(1,userFound.size());
		verify(userRepository,times(1))
				.findByUserNameAndAge(user.getUserName(),user.getAge());
	}
	@Test
	public void testCreatingNewUser(){
		when(userRepository.save(user)).thenReturn(user);
		userService.createUser(user);
		verify(userRepository,times(1)).save(user);
	}

	@Test
	public  void testDeleteUser(){
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		doNothing().when(userRepository).delete(user);
		userService.deleteUser(user.getId());
		verify(userRepository,times(1)).delete(user);
	}

	@Test
	public void testUpdateUser(){
		User userDto = new User();
		userDto.setUserName("testUser2");
		userDto.setAge(33);
		userDto.setEmail("testUser2@mail.com");
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);
		User userUpdated = userService.updateUser(user.getId(),userDto);
		assertEquals("testUser2",userUpdated.getUserName());
		assertEquals(33,userUpdated.getAge());
		assertEquals("testUser2@mail.com",userUpdated.getEmail());
	}
}
