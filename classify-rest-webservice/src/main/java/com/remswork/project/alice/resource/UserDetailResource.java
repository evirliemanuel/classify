package com.remswork.project.alice.resource;

import com.remswork.project.alice.dto.UserDto;
import com.remswork.project.alice.exception.UserDetailException;
import com.remswork.project.alice.model.UserDetail;
import com.remswork.project.alice.service.impl.UserDetailServiceImpl;
import mapfierj.Mapper;
import mapfierj.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Produces(value = {MediaType.APPLICATION_JSON})
@Consumes(value = {MediaType.APPLICATION_JSON})
@Path("userDetail")
public class UserDetailResource {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GET
    public Response getAll(@QueryParam("username") String username) {
        try {
            if (StringUtils.isEmpty(username)) {
                return Response.status(200).entity(userDetailService.getUserDetailList()).build();
            } else {
                UserDetail user = userDetailService.getByUsername(username);
                UserDto dto = new ModelMapper()
                        .set(user)
                        .exclude("teacher").getTransaction().mapAllTo(UserDto.class);
                if (user.getTeacher() != null) {
                    dto.setTeacherId(user.getTeacher().getId());
                }
                return Response.status(200).entity(dto).build();
            }
        } catch (UserDetailException e) {
            e.printStackTrace();
            return null;
        }
    }

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
