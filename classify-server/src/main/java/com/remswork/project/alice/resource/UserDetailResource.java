package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.UserDetailException;
import com.remswork.project.alice.model.UserDetail;
import com.remswork.project.alice.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("userDetail")
@Deprecated
public class UserDetailResource {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GET
    @Path("{userDetailId}")
    public UserDetail getUserDetailById(@PathParam("userDetailId") long userDetailId) {
        try {
            return userDetailService.getUserDetailById(userDetailId);
        } catch (UserDetailException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GET
    public List<UserDetail> getUserDetailList() {
        try {
            return userDetailService.getUserDetailList();
        } catch (UserDetailException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PUT
    @Path("{userDetailId}")
    public UserDetail updateUserDetailById(@PathParam("userDetailId") long userDetailId, UserDetail newUserDetail) {
        try {
            return userDetailService.updateUserDetailById(userDetailId, newUserDetail);
        } catch (UserDetailException e) {
            e.printStackTrace();
            return null;
        }
    }
}
