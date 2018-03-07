package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.TeacherException;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.DepartmentResourceLinks;
import com.remswork.project.alice.resource.links.TeacherResourceLinks;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON})
@Consumes(value = {MediaType.APPLICATION_JSON})
@Path("teacher")
public class TeacherResource {

    @Autowired
    private TeacherServiceImpl teacherService;

    @Context
    private UriInfo uriInfo;

    @QueryParam("departmentId")
    private long departmentId;

    @Autowired
    SessionFactory sessionFactory;

    @GET
    @Path("{teacherId}")
    public Response getTeacherById(@PathParam("teacherId") long id) {
        try {
            TeacherResourceLinks resourceLink = new TeacherResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            Teacher teacher = teacherService.getTeacherById(id);
            teacher.addLink(resourceLink.self(id));
            if (teacher.getDepartment() != null)
                teacher.getDepartment().addLink(departmentResourceLinks.self(teacher.getDepartment().getId()));
            return Response.status(Response.Status.OK).entity(teacher).build();
        } catch (TeacherException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getAll() {
        try {
            TeacherResourceLinks resourceLink = new TeacherResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            List<Teacher> teacherList = teacherService.getTeacherList();
            for (Teacher teacher : teacherList) {
                teacher.addLink(resourceLink.self(teacher.getId()));
                if (teacher.getDepartment() != null)
                    teacher.getDepartment().addLink(departmentResourceLinks.self(teacher.getDepartment().getId()));
            }
            GenericEntity<List<Teacher>> entity = new GenericEntity<List<Teacher>>(teacherList) {
            };
            return Response.status(Response.Status.OK).entity(entity).build();
        } catch (TeacherException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addTeacher(Teacher teacher) {
        try {
            TeacherResourceLinks resourceLink = new TeacherResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            if (departmentId > 0)
                teacher = teacherService.addTeacher(teacher, departmentId);
            else
                teacher = teacherService.addTeacher(teacher);
            teacher.addLink(resourceLink.self(teacher.getId()));
            if (teacher.getDepartment() != null)
                teacher.getDepartment().addLink(departmentResourceLinks.self(teacher.getDepartment().getId()));
            return Response.status(Response.Status.CREATED).entity(teacher).build();
        } catch (TeacherException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{teacherId}")
    public Response updateTeacherById(@PathParam("teacherId") long id, Teacher newTeacher) {
        try {
            TeacherResourceLinks resourceLink = new TeacherResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            Teacher teacher;
            if (departmentId > 0)
                teacher = teacherService.updateTeacherById(id, newTeacher, departmentId);
            else
                teacher = teacherService.updateTeacherById(id, newTeacher);
            teacher.addLink(resourceLink.self(id));
            if (teacher.getDepartment() != null)
                teacher.getDepartment().addLink(departmentResourceLinks.self(teacher.getDepartment().getId()));
            return Response.status(Response.Status.OK).entity(teacher).build();
        } catch (TeacherException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{teacherId}")
    public Response deleteTeacherById(@PathParam("teacherId") long id) {
        try {
            TeacherResourceLinks resourceLink = new TeacherResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            Teacher teacher = teacherService.deleteTeacherById(id);
            teacher.addLink(resourceLink.self(id));
            if (teacher.getDepartment() != null)
                teacher.getDepartment().addLink(departmentResourceLinks.self(teacher.getDepartment().getId()));
            return Response.status(Response.Status.OK).entity(teacher).build();
        } catch (TeacherException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
