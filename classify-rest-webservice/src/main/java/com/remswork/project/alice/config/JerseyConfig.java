package com.remswork.project.alice.config;

import com.remswork.project.alice.resource.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
//@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(TeacherResource.class);
		register(DepartmentResource.class);
		register(StudentResource.class);
		register(SubjectResource.class);
		register(ScheduleResource.class);
		register(SectionResource.class);
		register(ClassResource.class);
		register(ActivityResource.class);
		register(AssignmentResource.class);
		register(AttendanceResource.class);
		register(ExamResource.class);
		register(ProjectResource.class);
		register(QuizResource.class);
		register(RecitationResource.class);
		register(TermResource.class);
		register(FormulaResource.class);
		register(UserDetailResource.class);
		register(GradeResource.class);
		register(TestConrtoller.class);
	}
}
