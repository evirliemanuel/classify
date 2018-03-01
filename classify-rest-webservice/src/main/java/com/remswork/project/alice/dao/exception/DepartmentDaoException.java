package com.remswork.project.alice.dao.exception;

public class DepartmentDaoException extends RuntimeException {

    public DepartmentDaoException(){
        super();
    }

    public DepartmentDaoException(final String message){
        super(message);
    }
}
