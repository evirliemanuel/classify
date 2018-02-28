package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.AttendanceResult;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.AttendanceResultResourceLinks;
import com.remswork.project.alice.service.impl.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("result")
public class AttendanceResultResource {

    @Autowired
    private AttendanceServiceImpl attendanceService;
    private long studentId;
    private UriInfo uriInfo;

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @GET
    public Response getAttendanceResult(@PathParam("attendanceId") long attendanceId) {
        try {
            AttendanceResultResourceLinks resultResourceLinks = new AttendanceResultResourceLinks(uriInfo);
            AttendanceResult result =
                    attendanceService.getAttendanceResultByAttendanceAndStudentId(attendanceId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addAttendanceResult(@QueryParam("status") int status,
                                        @PathParam("attendanceId") long attendanceId) {
        try {
            AttendanceResultResourceLinks resultResourceLinks = new AttendanceResultResourceLinks(uriInfo);
            AttendanceResult result = attendanceService.addAttendanceResult(status, attendanceId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @PUT
    public Response updateAttendanceResult(@PathParam("attendanceId") long attendanceId,
                                           @QueryParam("status") int status) {
        try {
            AttendanceResultResourceLinks resultResourceLinks = new AttendanceResultResourceLinks(uriInfo);
            AttendanceResult result =
                    attendanceService.updateAttendanceResultByAttendanceAndStudentId(status, attendanceId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @DELETE
    public Response deleteAttendanceResult(@PathParam("attendanceId") long attendanceId) {
        try {
            AttendanceResultResourceLinks resultResourceLinks = new AttendanceResultResourceLinks(uriInfo);
            AttendanceResult result =
                    attendanceService.deleteAttendanceResultByAttendanceAndStudentId(attendanceId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
