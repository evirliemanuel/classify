package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.UserDetailDao;
import com.remswork.project.alice.dao.exception.UserDetailDaoException;
import com.remswork.project.alice.exception.UserDetailException;
import com.remswork.project.alice.model.UserDetail;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Deprecated
public class UserDetailDaoImpl implements UserDetailDao {

    @Autowired
    private SessionFactory sessionFactory;

    public UserDetail getByUsername(final String username) throws UserDetailException {
        final Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            final Query query = session.createQuery("from UserDetail where username=:username");
            query.setParameter("username", username);

            final UserDetail userDetail = (UserDetail) query.list().get(0);
            if (userDetail != null) {
                session.getTransaction().commit();
                return userDetail;
            } else {
                throw new UserDetailDaoException("No user found");
            }
        } catch (UserDetailDaoException e) {
            throw new UserDetailException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public UserDetail getUserDetailById(long id) throws UserDetailException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            UserDetail userDetail = session.get(UserDetail.class, id);
            if (userDetail == null)
                throw new UserDetailDaoException("UserDetail with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return userDetail;
        } catch (UserDetailDaoException e) {
            session.close();
            throw new UserDetailException(e.getMessage());
        }
    }

    @Override
    public List<UserDetail> getUserDetailList() throws UserDetailException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<UserDetail> userDetailList = new ArrayList<>();
            Query query = session.createQuery("from UserDetail");
            for (Object userDetailObj : query.list())
                userDetailList.add((UserDetail) userDetailObj);
            session.getTransaction().commit();
            session.close();
            return userDetailList;
        } catch (UserDetailDaoException e) {
            session.close();
            throw new UserDetailException(e.getMessage());
        }
    }

    @Override
    public UserDetail addUserDetail(UserDetail userDetail) throws UserDetailException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if (userDetail == null)
                throw new UserDetailDaoException("You tried to add userDetail with a null value");

            session.save(userDetail);
            session.getTransaction().commit();
            session.close();
            return userDetail;
        } catch (UserDetailDaoException e) {
            session.close();
            throw new UserDetailException(e.getMessage());
        }
    }

    @Override
    public UserDetail updateUserDetailById(long id, UserDetail newUserDetail) throws UserDetailException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            UserDetail userDetail = session.get(UserDetail.class, id);
            if (userDetail == null)
                throw new UserDetailDaoException("UserDetail with id : " + id + " does not exist.");
            if (newUserDetail == null)
                throw new UserDetailDaoException("You tried to update userDetail with a null value");
            if (!(newUserDetail.getUsername() != null ? newUserDetail.getUsername().trim() : "").isEmpty())
                userDetail.setUsername(newUserDetail.getUsername());
            if (!(newUserDetail.getPassword() != null ? newUserDetail.getPassword().trim() : "").isEmpty())
                userDetail.setPassword(newUserDetail.getPassword());

            session.getTransaction().commit();
            session.close();
            return userDetail;
        } catch (UserDetailDaoException e) {
            session.close();
            throw new UserDetailException(e.getMessage());
        }
    }

    @Override
    public UserDetail deleteUserDetailById(long id) throws UserDetailException {
        return null;
    }
}
