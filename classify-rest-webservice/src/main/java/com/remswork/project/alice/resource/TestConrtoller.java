package com.remswork.project.alice.resource;

import com.remswork.project.alice.dto.MarkDto;
import com.remswork.project.alice.model.*;
import com.remswork.project.alice.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
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
    ActivityServiceImpl activityService;

    @Autowired
    AttendanceServiceImpl attendanceService;

    @Autowired
    AssignmentServiceImpl assignmentService;

    @Autowired
    ExamServiceImpl examService;

    @Autowired
    ProjectServiceImpl projectService;

    @Autowired
    RecitationServiceImpl recitationService;

    @Autowired
    QuizServiceImpl quizService;


    @GET
    @Path("attendance")
    public Response attendance() {
        try {
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(10105, 964, 1);
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
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(10105, 964, 1);
            double act = (getActivity(993, activityService.getActivityListByClassId(995, 1)) *
                    (formula.getActivityPercentage() / 100.0));
            return Response.accepted().entity(act).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }

    @GET
    @Path("assignment")
    public Response assignment() {
        try {
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(10105, 964, 1);
            double act = (getAssignment(993, assignmentService.getAssignmentListByClassId(995, 1)) *
                    (formula.getAssignmentPercentage() / 100.0));
            return Response.accepted().entity(act).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }

    @GET
    @Path("exam")
    public Response exam() {
        try {
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(10105, 964, 1);
            double act = (getExam(993, examService.getExamListByClassId(995, 1)) *
                    (formula.getExamPercentage() / 100.0));
            return Response.accepted().entity(act).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }

    @GET
    @Path("project")
    public Response project() {
        try {
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(10105, 964, 1);
            double act = (getProject(993, projectService.getProjectListByClassId(995, 1)) *
                    (formula.getProjectPercentage() / 100.0));
            return Response.accepted().entity(act).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }

    @GET
    @Path("recitation")
    public Response recitation() {
        try {
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(10105, 964, 1);
            double act = (getRecitation(993, recitationService.getRecitationListByClassId(995, 1)) *
                    (formula.getRecitationPercentage() / 100.0));
            return Response.accepted().entity(act).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }

    @GET
    @Path("quiz")
    public Response quiz() {
        try {
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(10105, 964, 1);
            double act = (getQuiz(993, quizService.getQuizListByClassId(995, 1)) *
                    (formula.getQuizPercentage() / 100.0));
            return Response.accepted().entity(act).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }


    @GET
    @Path("total/class/{classId}/term/{termId}/teacher/{teacherId}/subject/{subjectId}/student/{studentId}")
    public Response total(@PathParam("classId") long classId,@PathParam("termId") long termId,@PathParam("teacherId") long teacherId,@PathParam("subjectId") long subjectId,@PathParam("studentId") long studentId) {
        try {
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(subjectId, teacherId, termId);
            double act_ = getActivity(studentId, activityService.getActivityListByClassId(classId, termId));
            double att_ =   getAttendance(studentId, attendanceService.getAttendanceListByClassId(classId, termId));
            double ass_ = getAssignment(studentId, assignmentService.getAssignmentListByClassId(classId, termId));
            double exa_ = getExam(studentId, examService.getExamListByClassId(classId, termId));
            double pro_ = getProject(studentId, projectService.getProjectListByClassId(classId, termId));
            double rec_ = getRecitation(studentId, recitationService.getRecitationListByClassId(classId, termId));
            double qui_ = getQuiz(studentId, quizService.getQuizListByClassId(classId, termId));



            double act = formula.getActivityPercentage() == 0 ? 0 :  act_ == 0 ? 0 : (act_ * ((double)formula.getActivityPercentage() / 100.0));
            double att = formula.getAttendancePercentage() == 0 ? 0 : att_ == 0 ? 0 : (att_ * ((double) formula.getAttendancePercentage() / 100.0));
            double ass = formula.getAssignmentPercentage() == 0 ? 0 : ass_ == 0 ? 0 : (ass_ *
                    (formula.getAssignmentPercentage() / 100.0));
            double exa = formula.getExamPercentage() == 0 ? 0 : exa_ == 0 ? 0 : (exa_ * ((double)formula.getExamPercentage() / 100.0));
            double pro = formula.getProjectPercentage() == 0 ? 0 : pro_ == 0 ? 0 :(pro_ * ((double)formula.getProjectPercentage() / 100.0));
            double rec = formula.getRecitationPercentage() == 0 ? 0 : rec_ == 0 ? 0 : ( rec_ * ((double)formula.getRecitationPercentage() / 100.0));
            double qui = formula.getQuizPercentage() == 0 ? 0 : qui_ == 0 ? 0 :  (qui_ * ((double)formula.getQuizPercentage() / 100.0));

            double total = act + att + ass + exa + pro + rec + qui;

            return Response.accepted().entity(total).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }

    @GET
    @Path("all/class/{classId}/teacher/{teacherId}/subject/{subjectId}/student/{studentId}")
    public Response all(@PathParam("classId") long classId,@PathParam("teacherId") long teacherId,@PathParam("subjectId") long subjectId,@PathParam("studentId") long studentId) {
        try {
            MarkDto markDto = new MarkDto();
            double m1 = midterm(classId, teacherId, subjectId, studentId);
            double f1 = finalterm(classId, teacherId, subjectId, studentId);
            String m = m1 + "";
            if (m.equalsIgnoreCase("NaN")) {
                m = "0.0";
            }
            String f = f1 + "";
            if (f.equalsIgnoreCase("NaN") || f.equalsIgnoreCase("0")) {
                f = "0.0";
            }
            if (!f.equalsIgnoreCase("0.0")) {
                f = ((m1 + f1) / 2) + "";
            }
            markDto.setMidterm(m);
            markDto.setFinalterm(f);
            return Response.accepted().entity(markDto).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.accepted().entity(0).build();
        }
    }

    public double midterm(long classId,long teacherId,long subjectId,long studentId) {
        try {
        Formula formula = formulaService.getFormulaBySubjectAndTeacherId(subjectId, teacherId, 1);
        double act_ = getActivity(studentId, activityService.getActivityListByClassId(classId, 1));
        double att_ =   getAttendance(studentId, attendanceService.getAttendanceListByClassId(classId, 1));
        double ass_ = getAssignment(studentId, assignmentService.getAssignmentListByClassId(classId, 1));
        double exa_ = getExam(studentId, examService.getExamListByClassId(classId, 1));
        double pro_ = getProject(studentId, projectService.getProjectListByClassId(classId, 1));
        double rec_ = getRecitation(studentId, recitationService.getRecitationListByClassId(classId, 1));
        double qui_ = getQuiz(studentId, quizService.getQuizListByClassId(classId, 1));



        double act = formula.getActivityPercentage() == 0 ? 0 :  act_ == 0 ? 0 : (act_ * ((double)formula.getActivityPercentage() / 100.0));
        double att = formula.getAttendancePercentage() == 0 ? 0 : att_ == 0 ? 0 : (att_ * ((double) formula.getAttendancePercentage() / 100.0));
        double ass = formula.getAssignmentPercentage() == 0 ? 0 : ass_ == 0 ? 0 : (ass_ *
                (formula.getAssignmentPercentage() / 100.0));
        double exa = formula.getExamPercentage() == 0 ? 0 : exa_ == 0 ? 0 : (exa_ * ((double)formula.getExamPercentage() / 100.0));
        double pro = formula.getProjectPercentage() == 0 ? 0 : pro_ == 0 ? 0 :(pro_ * ((double)formula.getProjectPercentage() / 100.0));
        double rec = formula.getRecitationPercentage() == 0 ? 0 : rec_ == 0 ? 0 : ( rec_ * ((double)formula.getRecitationPercentage() / 100.0));
        double qui = formula.getQuizPercentage() == 0 ? 0 : qui_ == 0 ? 0 :  (qui_ * ((double)formula.getQuizPercentage() / 100.0));

        return act + att + ass + exa + pro + rec + qui;
        } catch (Exception e) {
            return 0;
        }
    }

    public double finalterm(long classId,long teacherId,long subjectId,long studentId) {
        try {
            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(subjectId, teacherId, 2);
            double act_ = getActivity(studentId, activityService.getActivityListByClassId(classId, 2));
            double att_ =   getAttendance(studentId, attendanceService.getAttendanceListByClassId(classId, 2));
            double ass_ = getAssignment(studentId, assignmentService.getAssignmentListByClassId(classId, 2));
            double exa_ = getExam(studentId, examService.getExamListByClassId(classId, 2));
            double pro_ = getProject(studentId, projectService.getProjectListByClassId(classId, 2));
            double rec_ = getRecitation(studentId, recitationService.getRecitationListByClassId(classId, 2));
            double qui_ = getQuiz(studentId, quizService.getQuizListByClassId(classId, 2));



            double act = formula.getActivityPercentage() == 0 ? 0 :  act_ == 0 ? 0 : (act_ * ((double)formula.getActivityPercentage() / 100.0));
            double att = formula.getAttendancePercentage() == 0 ? 0 : att_ == 0 ? 0 : (att_ * ((double) formula.getAttendancePercentage() / 100.0));
            double ass = formula.getAssignmentPercentage() == 0 ? 0 : ass_ == 0 ? 0 : (ass_ *
                    (formula.getAssignmentPercentage() / 100.0));
            double exa = formula.getExamPercentage() == 0 ? 0 : exa_ == 0 ? 0 : (exa_ * ((double)formula.getExamPercentage() / 100.0));
            double pro = formula.getProjectPercentage() == 0 ? 0 : pro_ == 0 ? 0 :(pro_ * ((double)formula.getProjectPercentage() / 100.0));
            double rec = formula.getRecitationPercentage() == 0 ? 0 : rec_ == 0 ? 0 : ( rec_ * ((double)formula.getRecitationPercentage() / 100.0));
            double qui = formula.getQuizPercentage() == 0 ? 0 : qui_ == 0 ? 0 :  (qui_ * ((double)formula.getQuizPercentage() / 100.0));

            return act + att + ass + exa + pro + rec + qui;
        } catch (Exception e) {
            return 0;
        }
    }

    public double getAttendance(long studentId, List<Attendance> list) {
        try {
            int count = 0;
            if (list != null && list.size() > 0) {
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
        } catch (Exception e) {
            return 0;
        }
    }

    public double getActivity(long studentId, List<Activity> list) {
        try {
            int total = 0;
            int count = 0;
            if (list != null && list.size() > 0) {
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
        } catch (Exception e) {
            return 0;
        }
    }

    public double getExam(long studentId, List<Exam> list) {
        try {
            int total = 0;
            int count = 0;
            if (list != null && list.size() > 0) {
                for (Exam exam : list) {
                    try {
                        ExamResult result;
                        if ((result = examService.getExamResultByExamAndStudentId(exam.getId(), studentId)) != null) {
                            count += result.getScore();
                            total += exam.getItemTotal();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return ((double) count / total) * 100;
            } else {
                return count;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public double getAssignment(long studentId, List<Assignment> list) {
        try {
            int total = 0;
            int count = 0;
            if (list != null && list.size() > 0) {
                for (Assignment assignment : list) {
                    try {
                        AssignmentResult result;
                        if ((result = assignmentService.getAssignmentResultByAssignmentAndStudentId(assignment.getId(), studentId)) != null) {
                            count += result.getScore();
                            total += assignment.getItemTotal();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return ((double) count / total) * 100;
            } else {
                return count;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public double getProject(long studentId, List<Project> list) {
        try {
            int total = 0;
            int count = 0;
            if (list != null && list.size() > 0) {
                for (Project project : list) {
                    try {
                        ProjectResult result;
                        if ((result = projectService.getProjectResultByProjectAndStudentId(project.getId(), studentId)) != null) {
                            count += result.getScore();
                            total += project.getItemTotal();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return ((double) count / total) * 100;
            } else {
                return count;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public double getQuiz(long studentId, List<Quiz> list) {
        try {
            int total = 0;
            int count = 0;
            if (list != null && list.size() > 0) {
                for (Quiz quiz : list) {
                    try {
                        QuizResult result;
                        if ((result = quizService.getQuizResultByQuizAndStudentId(quiz.getId(), studentId)) != null) {
                            count += result.getScore();
                            total += quiz.getItemTotal();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return ((double) count / total) * 100;
            } else {
                return count;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public double getRecitation(long studentId, List<Recitation> list) {
        try {
            int total = 0;
            int count = 0;
            if (list != null && list.size() > 0) {
                for (Recitation recitation : list) {
                    try {
                        RecitationResult result;
                        if ((result = recitationService.getRecitationResultByRecitationAndStudentId(recitation.getId(), studentId)) != null) {
                            count += result.getScore();
                            total += recitation.getItemTotal();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return ((double) count / total) * 100;
            } else {
                return count;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
