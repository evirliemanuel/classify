package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.UserDetailException;
import com.remswork.project.alice.model.UserDetail;

import java.util.List;

public interface UserDetailDao {

    UserDetail getUserDetailById(long id) throws UserDetailException;
    List<UserDetail> getUserDetailList() throws UserDetailException;
    UserDetail addUserDetail(UserDetail userDetail) throws UserDetailException;
    UserDetail updateUserDetailById(long id, UserDetail newUserDetail) throws UserDetailException;
    UserDetail deleteUserDetailById(long id) throws UserDetailException;
}
