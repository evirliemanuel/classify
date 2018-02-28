package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Attendance;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.AttendanceResourceLinks;
import com.remswork.project.alice.resource.links.ClassResourceLinks;
import com.remswork.project.alice.service.impl.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("attendance")
public class AttendanceResource {

    @Autowired
    private AttendanceServiceImpl attendanceService;
    @Autowired
    private AttendanceResultResource attendanceResultResource;
    @Context
    private UriInfo uriInfo;
    @QueryParam("classId")
    private long classId;
    @QueryParam("termId")
    private long termId;
    @QueryParam("studentId")
    private long studentId;

    @GET
    @Path("{attendanceId}")
    public Response getAttendanceById(@PathParam("attendanceId") long id) {
        try {
            AttendanceResourceLinks resourceLinks = new AttendanceResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Attendance attendance = attendanceService.getAttendanceById(id);
            attendance.addLink(resourceLinks.self(id));
            if(attendance.get_class() != null)
                attendance.get_class().addLink(classResourceLinks.self(attendance.get_class().getId()));
            return Response.status(Response.Status.OK).entity(attendance).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getAttendanceList() {
        try {
            List<Attendance> attendanceList;
            AttendanceResourceLinks resourceLinks = new AttendanceResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(classId != 0 && termId != 0)
                attendanceList = attendanceService.getAttendanceListByClassId(classId, termId);
            else if(classId != 0)
                attendanceList = attendanceService.getAttendanceListByClassId(classId);
            else if(studentId != 0 && termId != 0)
                attendanceList = attendanceService.getAttendanceListByStudentId(studentId, termId);
            else if(studentId != 0)
                attendanceList = attendanceService.getAttendanceListByStudentId(studentId);
            else
                attendanceList = attendanceService.getAttendanceList();
            for(Attendance attendance : attendanceList) {
                attendance.addLink(resourceLinks.self(attendance.getId()));
                if(attendance.get_class() != null)
                    attendance.get_class().addLink(classResourceLinks.self(attendance.get_class().getId()));
            }
            GenericEntity<List<Attendance>> entity = new GenericEntity<List<Attendance>>(attendanceList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addAttendance(Attendance attendance) {
        try {
            AttendanceResourceLinks resourceLinks = new AttendanceResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(termId > 0)
                attendance = attendanceService.addAttendance(attendance, classId, termId);
            else
                attendance = attendanceService.addAttendance(attendance, classId);
            attendance.addLink(resourceLinks.self(attendance.getId()));
            if(attendance.get_class() != null)
                attendance.get_class().addLink(classResourceLinks.self(attendance.get_class().getId()));
            return Response.status(Response.Status.CREATED).entity(attendance).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{attendanceId}")
    public Response updateAttendanceById(@PathParam("attendanceId") long id, Attendance newAttendance) {
        try {
            AttendanceResourceLinks resourceLinks = new AttendanceResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);
            Attendance attendance;
            if(termId > 0)
                attendance = attendanceService.updateAttendanceById(id, newAttendance, classId, termId);
            else
                attendance = attendanceService.updateAttendanceById(id, newAttendance, classId);
            attendance.addLink(resourceLinks.self(attendance.getId()));
            if(attendance.get_class() != null)
                attendance.get_class().addLink(classResourceLinks.self(attendance.get_class().getId()));
            return Response.status(Response.Status.OK).entity(attendance).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{attendanceId}")
    public Response deleteAttendanceById(@PathParam("attendanceId") long id) {
        try {
            AttendanceResourceLinks resourceLinks = new AttendanceResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Attendance attendance = attendanceService.deleteAttendanceById(id);
            attendance.addLink(resourceLinks.self(attendance.getId()));
            if(attendance.get_class() != null)
                attendance.get_class().addLink(classResourceLinks.self(attendance.get_class().getId()));
            return Response.status(Response.Status.OK).entity(attendance).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @Path("{attendanceId}/result")
    public AttendanceResultResource attendanceResultResource() {
        attendanceResultResource.setStudentId(studentId);
        attendanceResultResource.setUriInfo(uriInfo);
        return attendanceResultResource;
    }
}