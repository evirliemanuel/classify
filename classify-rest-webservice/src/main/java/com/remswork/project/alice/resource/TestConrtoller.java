package com.remswork.project.alice.resource;

import com.remswork.project.alice.model.*;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;
import com.remswork.project.alice.service.impl.AttendanceServiceImpl;
import com.remswork.project.alice.service.impl.FormulaServiceImpl;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("test")
public class TestConrtoller {

    @Autowired
    FormulaServiceImpl formulaService;

    @Autowired
    TeacherServiceImpl teacherService;

    @Autowired
    AttendanceServiceImpl attendanceService;

    @Autowired
    ActivityServiceImpl activityService;

    @GET
    @Path("attendance")
    public Response test() {
        try {
            int cat = 0;
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(10105, 964, 1);
            if (formula.getActivityPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getAssignmentPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getAttendancePercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getExamPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getProjectPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getQuizPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getRecitationPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getTotalPercentage() > 0) {
                cat = cat + 1;
            }
            double att = (getAttendance(993, attendanceService.getAttendanceListByClassId(995, 1)) *
                    ((double) formula.getAttendancePercentage() / 100.0));
            return Response.accepted().entity(att).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }

    @GET
    @Path("activity")
    public Response activity() {
        try {
            int cat = 0;
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(10105, 964, 1);
            if (formula.getActivityPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getAssignmentPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getAttendancePercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getExamPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getProjectPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getQuizPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getRecitationPercentage() > 0) {
                cat = cat + 1;
            }
            if (formula.getTotalPercentage() > 0) {
                cat = cat + 1;
            }
            double act = (getActivity(993, activityService.getActivityListByClassId(995, 1)) *
                    (formula.getActivityPercentage() / 100.0));
            return Response.accepted().entity(act).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }

    @GET
    @Path("total")
    public Response total() {
        try {
            int grade = 50;
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(10105, 964, 1);
            double act = (getActivity(993, activityService.getActivityListByClassId(995, 1)) *
                    (formula.getActivityPercentage() / 100.0));
            double att = (getAttendance(993, attendanceService.getAttendanceListByClassId(995, 1)) *
                    ((double) formula.getAttendancePercentage() / 100.0));
            return Response.accepted().entity((act + att)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }

    public double getAttendance(long studentId, List<Attendance> list) {
        int count = 0;
        if (list != null) {
            for (Attendance attendance : list) {
                try {
                    AttendanceResult result;
                    if ((result = attendanceService.getAttendanceResultByAttendanceAndStudentId(attendance.getId(), studentId)) != null) {
                        if (result.getStatus() == 1) {
                            count++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return ((double) count / list.size()) * 100;
        } else {
            return count;
        }
    }

    public double getActivity(long studentId, List<Activity> list) {
        int total = 0;
        int count = 0;
        if (list != null) {
            for (Activity activity : list) {
                try {
                    ActivityResult result;
                    if ((result = activityService.getActivityResultByActivityAndStudentId(activity.getId(), studentId)) != null) {
                        count += result.getScore();
                        total += activity.getItemTotal();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return ((double) count / total) * 100;
        } else {
            return count;
        }
    }
}
