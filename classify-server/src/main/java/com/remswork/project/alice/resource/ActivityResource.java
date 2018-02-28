package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ActivityResourceLinks;
import com.remswork.project.alice.resource.links.ClassResourceLinks;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("activity")
public class ActivityResource {

    @Autowired
    private ActivityServiceImpl activityService;
    @Autowired
    private ActivityResultResource activityResultResource;
    @Context
    private UriInfo uriInfo;
    @QueryParam("classId")
    private long classId;
    @QueryParam("termId")
    private long termId;
    @QueryParam("studentId")
    private long studentId;

    @GET
    @Path("{activityId}")
    public Response getActivityById(@PathParam("activityId") long id) {
        try {
            ActivityResourceLinks resourceLinks = new ActivityResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Activity activity = activityService.getActivityById(id);
            activity.addLink(resourceLinks.self(id));
            if(activity.get_class() != null)
                activity.get_class().addLink(classResourceLinks.self(activity.get_class().getId()));
            return Response.status(Response.Status.OK).entity(activity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getActivityList() {
        try {
            List<Activity> activityList;
            ActivityResourceLinks resourceLinks = new ActivityResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(classId != 0 && termId != 0)
                activityList = activityService.getActivityListByClassId(classId, termId);
            else if(classId != 0)
                activityList = activityService.getActivityListByClassId(classId);
            else if(studentId != 0 && termId != 0)
                activityList = activityService.getActivityListByStudentId(studentId, termId);
            else if(studentId != 0)
                activityList = activityService.getActivityListByStudentId(studentId);
            else
                activityList = activityService.getActivityList();
            for(Activity activity : activityList) {
                activity.addLink(resourceLinks.self(activity.getId()));
                if(activity.get_class() != null)
                    activity.get_class().addLink(classResourceLinks.self(activity.get_class().getId()));
            }
            GenericEntity<List<Activity>> entity = new GenericEntity<List<Activity>>(activityList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addActivity(Activity activity) {
        try {
            ActivityResourceLinks resourceLinks = new ActivityResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(termId > 0)
                activity = activityService.addActivity(activity, classId, termId);
            else
                activity = activityService.addActivity(activity, classId);
            activity.addLink(resourceLinks.self(activity.getId()));
            if(activity.get_class() != null)
                activity.get_class().addLink(classResourceLinks.self(activity.get_class().getId()));
            return Response.status(Response.Status.CREATED).entity(activity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{activityId}")
    public Response updateActivityById(@PathParam("activityId") long id, Activity newActivity) {
        try {
            ActivityResourceLinks resourceLinks = new ActivityResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);
            Activity activity;
            if(termId > 0)
                activity = activityService.updateActivityById(id, newActivity, classId, termId);
            else
                activity = activityService.updateActivityById(id, newActivity, classId);
            activity.addLink(resourceLinks.self(activity.getId()));
            if(activity.get_class() != null)
                activity.get_class().addLink(classResourceLinks.self(activity.get_class().getId()));
            return Response.status(Response.Status.OK).entity(activity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{activityId}")
    public Response deleteActivityById(@PathParam("activityId") long id) {
        try {
            ActivityResourceLinks resourceLinks = new ActivityResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Activity activity = activityService.deleteActivityById(id);
            activity.addLink(resourceLinks.self(activity.getId()));
            if(activity.get_class() != null)
                activity.get_class().addLink(classResourceLinks.self(activity.get_class().getId()));
            return Response.status(Response.Status.OK).entity(activity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @Path("{activityId}/result")
    public ActivityResultResource activityResultResource() {
        activityResultResource.setStudentId(studentId);
        activityResultResource.setUriInfo(uriInfo);
        return activityResultResource;
    }
}
