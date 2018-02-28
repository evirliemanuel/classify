package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.ScheduleException;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ScheduleResourceLinks;
import com.remswork.project.alice.service.impl.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Set;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("schedule")
public class ScheduleResource {

    @Autowired
    private ScheduleServiceImpl scheduleService;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("{scheduleId}")
    public Response getScheduleById(@PathParam("scheduleId") long id) {
        try {
            ScheduleResourceLinks resourceLinks = new ScheduleResourceLinks(uriInfo);
            Schedule schedule = scheduleService.getScheduleById(id);
            schedule.addLink(resourceLinks.self(id));
            return Response.status(Response.Status.OK).entity(schedule).build();
        }catch (ScheduleException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    @Path("1")
    public Response getScheduleListByTeacherId(@QueryParam("teacherId") long teacherId) {
        try {
            ScheduleResourceLinks resourceLinks = new ScheduleResourceLinks(uriInfo);
            Set<Schedule> scheduleSet = scheduleService.getScheduleListByTeacherId(teacherId);
            for(Schedule schedule : scheduleSet)
                schedule.addLink(resourceLinks.self(schedule.getId()));
            GenericEntity<Set<Schedule>> entity = new GenericEntity<Set<Schedule>>(scheduleSet){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (ScheduleException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getScheduleList() {
        try {
            ScheduleResourceLinks resourceLinks = new ScheduleResourceLinks(uriInfo);
            List<Schedule> scheduleList = scheduleService.getScheduleList();
            for(Schedule schedule : scheduleList)
                schedule.addLink(resourceLinks.self(schedule.getId()));
            GenericEntity<List<Schedule>> entity = new GenericEntity<List<Schedule>>(scheduleList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (ScheduleException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addSchedule(Schedule schedule) {
        try {
            ScheduleResourceLinks resourceLinks = new ScheduleResourceLinks(uriInfo);
            schedule = scheduleService.addSchedule(schedule);
            schedule.addLink(resourceLinks.self(schedule.getId()));
            return Response.status(Response.Status.CREATED).entity(schedule).build();
        }catch (ScheduleException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{scheduleId}")
    public Response updateScheduleById(@PathParam("scheduleId") long id, Schedule newSchedule) {
        try {
            ScheduleResourceLinks resourceLinks = new ScheduleResourceLinks(uriInfo);
            Schedule schedule = scheduleService.updateScheduleById(id, newSchedule);
            schedule.addLink(resourceLinks.self(id));
            return Response.status(Response.Status.OK).entity(schedule).build();
        }catch (ScheduleException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{scheduleId}")
    public Response deleteScheduleById(@PathParam("scheduleId") long id) {
        try {
            ScheduleResourceLinks resourceLinks = new ScheduleResourceLinks(uriInfo);
            Schedule schedule = scheduleService.deleteScheduleById(id);
            schedule.addLink(resourceLinks.self(id));
            return Response.status(Response.Status.OK).entity(schedule).build();
        }catch (ScheduleException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

}
