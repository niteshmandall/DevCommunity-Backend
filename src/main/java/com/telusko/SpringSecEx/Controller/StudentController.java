package com.telusko.SpringSecEx.Controller;


import com.telusko.SpringSecEx.Entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor

public class StudentController {

    private List<Student> students = new ArrayList<>(List.of(
            new Student(1, "Nitesh", 66),
            new Student(2, "Raju", 88)

    ));

    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }
}
