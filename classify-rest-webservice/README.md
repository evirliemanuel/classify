## Available APIs

#### Get information about the teachers
```
http:localhost:8080/api/teachers
http:localhost:8080/api/teachers/{id}
http:localhost:8080/api/teachers/{id}/users
http:localhost:8080/api/teachers/{id}/subjects
http:localhost:8080/api/teachers/{id}/subjects/{subjectId}
http:localhost:8080/api/teachers/{id}/lessons
http:localhost:8080/api/teachers/{id}/lessons/{lessonId}
http:localhost:8080/api/teachers/{id}/lessons/{lessonId}/subjects
http:localhost:8080/api/teachers/{id}/lessons/{lessonId}/students
http:localhost:8080/api/teachers/{id}/lessons/{lessonId}/students/{studentId}
```

#### Get information about the students
```
http:localhost:8080/api/students
http:localhost:8080/api/students/{id}
http:localhost:8080/api/students/{id}/users
http:localhost:8080/api/students/{id}/subjects
http:localhost:8080/api/students/{id}/subjects/{subjectId}
http:localhost:8080/api/students/{id}/teachers
http:localhost:8080/api/students/{id}/teachers/{teacherId}
http:localhost:8080/api/students/{id}/lessons
http:localhost:8080/api/students/{id}/lessons/{lessonId}
http:localhost:8080/api/students/{id}/lessons/{lessonId}/teachers
http:localhost:8080/api/students/{id}/lessons/{lessonId}/subjects
```

#### Get information about the subjects
```
http:localhost:8080/api/subjects
http:localhost:8080/api/subjects/{id}
http:localhost:8080/api/subjects/{id}/students
http:localhost:8080/api/subjects/{id}/students/{studentId}
http:localhost:8080/api/subjects/{id}/teacher
http:localhost:8080/api/subjects/{id}/teacher/{teacherId}
```

#### Get information about the lessons
```
http:localhost:8080/api/lessons
http:localhost:8080/api/lessons/{id}
http:localhost:8080/api/lessons/{id}/subjects
http:localhost:8080/api/lessons/{id}/teacher
http:localhost:8080/api/lessons/{id}/students
http:localhost:8080/api/lessons/{id}/students/{studentId}

```
