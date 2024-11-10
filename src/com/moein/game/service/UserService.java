package com.moein.game.service;

import com.moein.game.entity.User;
import com.moein.game.exception.DuplicateException;
import com.moein.game.exception.NotFoundException;
import com.moein.game.repository.CrudRepository;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserService {
    @EJB
    private CrudRepository<User, Integer> crudRepository;
    @Resource
    private UserTransaction transaction;

    public void createUser(User user) throws DuplicateException, SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        if (!findUsersByUsername(user.getUsername()).isEmpty()) {
            throw new DuplicateException("Username used before");
        }
        transaction.begin();
        crudRepository.save(user);
        transaction.commit();
    }
    public User findUserByUsernameAndPassword(String username, String password) throws NotFoundException {
        List<User> userList = findUsersByUsername(username);
        if (userList.isEmpty()) {
            throw new NotFoundException("User Not Found By Username");
        } else if (!userList.get(0).getPassword().equals(password)) {
            throw new NotFoundException("Incorrect Password");
        }
        return userList.get(0);
    }
    public List<User> findUsersByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", username);
        List<User> userList = crudRepository.findAll(User.class,"entity.username=:content", params);
        return userList;
    }
}
