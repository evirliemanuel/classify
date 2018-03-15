package com.remswork.project.alice.web.service;

import java.util.List;

import com.remswork.project.alice.model.UserDetail;
import com.remswork.project.alice.web.service.exception.UserDetailServiceException;

public interface UserDetailService {

    UserDetail getUserDetailById(long id) throws UserDetailServiceException;
    List<UserDetail> getUserDetailList() throws UserDetailServiceException;
    UserDetail addUserDetail(UserDetail userDetail) throws UserDetailServiceException;
    UserDetail updateUserDetailById(long id, UserDetail newUserDetail) throws UserDetailServiceException;
    UserDetail deleteUserDetailById(long id) throws UserDetailServiceException;
}
