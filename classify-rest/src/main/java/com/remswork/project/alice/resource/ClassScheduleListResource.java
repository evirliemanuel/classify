package com.remswork.project.alice.resource;

import com.remswork.project.alice.dao.exception.ClassDaoException;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ClassScheduleListResourceLinks;
import com.remswork.project.alice.service.impl.ClassServiceImpl;
import com.remswork.project.alice.service.impl.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Set;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("schedule")
public class ClassScheduleListResource {

    @Autowired
    private ClassServiceImpl classService;
    @Autowired
    private ScheduleServiceImpl scheduleService;
    private UriInfo uriInfo;
    private long scheduleId;

    void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @GET
    @Path("{scheduleId}")
    public Response getScheduleById(@PathParam("classId") long classId, @PathParam("scheduleId") long id) {
        try {
            ClassScheduleListResourceLinks resourceLinks = new ClassScheduleListResourceLinks(uriInfo);
            Schedule schedule = classService.getScheduleById(classId, id);
            schedule.addLink(resourceLinks.self(classId, id));
            return Response.status(Response.Status.OK).entity(schedule).build();
        }catch (ClassException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getScheduleList(@PathParam("classId") long classId) {
        try {
            ClassScheduleListResourceLinks resourceLinks = new ClassScheduleListResourceLinks(uriInfo);
            Set<Schedule> scheduleSet = classService.getScheduleList(classId);
            for(Schedule schedule : scheduleSet)
                schedule.addLink(resourceLinks.self(classId, schedule.getId()));
            GenericEntity<Set<Schedule>> entity = new GenericEntity<Set<Schedule>>(scheduleSet){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (ClassException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addScheduleById(@PathParam("classId") long classId) {
        try {
            ClassScheduleListResourceLinks resourceLinks = new ClassScheduleListResourceLinks(uriInfo);
            if(scheduleId == 0)
                throw new ClassDaoException("Query param : scheduleId is required");
            Schedule schedule = classService.addScheduleById(classId, scheduleId);
            schedule.addLink(resourceLinks.self(classId, scheduleId));
            return Response.status(Response.Status.OK).entity(schedule).build();
        }catch (ClassException | ClassDaoException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{scheduleId}")
    public Response deleteScheduleById(@PathParam("classId") long classId, @PathParam("scheduleId") long id) {
        try {
            ClassScheduleListResourceLinks resourceLinks = new ClassScheduleListResourceLinks(uriInfo);
            Schedule schedule = classService.deleteScheduleById(classId, id);
            schedule.addLink(resourceLinks.self(classId, id));
            return Response.status(Response.Status.OK).entity(schedule).build();
        }catch (ClassException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

}
