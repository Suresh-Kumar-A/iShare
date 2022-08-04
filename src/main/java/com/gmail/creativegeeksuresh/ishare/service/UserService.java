package com.gmail.creativegeeksuresh.ishare.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.gmail.creativegeeksuresh.ishare.dto.UserDto;
import com.gmail.creativegeeksuresh.ishare.exception.InvalidCredentialsException;
import com.gmail.creativegeeksuresh.ishare.exception.InvalidUserException;
import com.gmail.creativegeeksuresh.ishare.exception.UserAlreadyExistsException;
import com.gmail.creativegeeksuresh.ishare.model.User;
import com.gmail.creativegeeksuresh.ishare.repository.UserRepository;
import com.gmail.creativegeeksuresh.ishare.service.util.AppConstants;
import com.gmail.creativegeeksuresh.ishare.service.util.CustomUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CustomUtils customUtils;

  @Autowired
  private RoleService roleService;

  public User createUser(UserDto request) throws UserAlreadyExistsException, Exception {
    if (userRepository.findByUsername(request.getUsername()) != null)
      throw new UserAlreadyExistsException("User with similar data exists");
    User newUser = new User();
    BeanUtils.copyProperties(request, newUser);
    newUser.setPassword(customUtils.encodeUsingBcryptPasswordEncoder(request.getPassword()));
    newUser.setUid(customUtils.generateToken());
    newUser.setCreatedAt(new Date());
    newUser.setRole(roleService.findByRoleName(AppConstants.ROLE_USER));
    return userRepository.save(newUser);
  }

  public User findByUsername(String username) throws InvalidUserException, Exception {
    User user = userRepository.findByUsername(username);
    if (user == null)
      throw new InvalidUserException("User does not exists");
    else
      return user;
  }

  public User validateUserCredentials(UserDto user)
      throws InvalidCredentialsException, InvalidUserException, Exception {
    User dbUser = findByUsername(user.getUsername());
    if (dbUser != null) {
      if (customUtils.verifyUserPassword(user.getPassword(), dbUser.getPassword())) {
        dbUser.setPassword("");
        return dbUser;
      } else
        throw new InvalidCredentialsException("User Credentials are incorrect or invalid");
    } else
      throw new InvalidUserException("User does not exists");
  }

  public void createAdminUser(String adminUsername, String adminPassword, String adminEmail) throws Exception {
    if (userRepository.findByUsername(adminUsername) == null) {
      User adminUser = new User();
      adminUser.setUsername(adminUsername);
      adminUser.setPassword(customUtils.encodeUsingBcryptPasswordEncoder(adminPassword));
      adminUser.setUid(customUtils.generateToken());
      adminUser.setCreatedAt(new Date());
      adminUser.setRole(roleService.findByRoleName(AppConstants.ROLE_ADMIN));
      userRepository.save(adminUser);
    }
  }

  public List<User> getAllUsers() throws Exception {
    return (List<User>) userRepository.findAll();
  }

  public List<User> getAllUsersWithoutPassword() throws Exception {
    List<User> userList = getAllUsers();
    userList.stream().map(user -> {
      user.setPassword("");
      return user;
    }).collect(Collectors.toList());
    return userList;
  }

  public User findByUid(String uid) throws Exception {
    return userRepository.findByUid(uid);
  }

  public User findByUidWithoutPassword(String uid) throws Exception {
    User user = findByUid(uid);
    user.setPassword("");
    return user;
  }

  private void deleteUser(User user) throws Exception {
    userRepository.delete(user);
  }

  public void deleteByUid(String uid) throws InvalidUserException, Exception {
    User user = findByUid(uid);
    if (user != null)
      deleteUser(user);
    else
      throw new InvalidUserException("User Does not Exists");
  }

  public User updateUser(UserDto user) throws InvalidUserException, Exception {
    User temp = findByUid(user.getUid());
    if (temp != null) {
      BeanUtils.copyProperties(user, temp, "uid", "sno", "createdAt", "role");
      temp.setPassword(customUtils.encodeUsingBcryptPasswordEncoder(user.getPassword()));

      return userRepository.save(temp);
    } else
      throw new InvalidUserException("User Does not Exists");
  }

  public void bulkImportUsers(List<User> userList) throws Exception {
    userRepository.saveAll(userList);
  }
}
