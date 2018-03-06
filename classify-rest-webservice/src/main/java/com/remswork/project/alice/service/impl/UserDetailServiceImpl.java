package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.UserDetailDaoImpl;
import com.remswork.project.alice.exception.UserDetailException;
import com.remswork.project.alice.model.UserDetail;
import com.remswork.project.alice.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Deprecated
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailDaoImpl userDetailDao;

    public UserDetail getByUsername(String username) throws UserDetailException {
        return userDetailDao.getByUsername(username);
    }

    @Override
    public UserDetail getUserDetailById(long id) throws UserDetailException {
        return userDetailDao.getUserDetailById(id);
    }

    @Override
    public List<UserDetail> getUserDetailList() throws UserDetailException {
        return userDetailDao.getUserDetailList();
    }

    @Override
    public UserDetail addUserDetail(UserDetail userDetail) throws UserDetailException {
        return null;
    }

    @Override
    public UserDetail updateUserDetailById(long id, UserDetail newUserDetail) throws UserDetailException {
        return userDetailDao.updateUserDetailById(id, newUserDetail);
    }

    @Override
    public UserDetail deleteUserDetailById(long id) throws UserDetailException {
        return null;
    }
}
