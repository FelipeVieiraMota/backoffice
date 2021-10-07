package com.motafelipe.api.backoffice.controller;

import com.motafelipe.api.backoffice.models.EnvelopedData;
import com.motafelipe.api.backoffice.models.StudentModel;
import com.motafelipe.api.backoffice.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javassist.NotFoundException;

@RestController
@RequestMapping(value="/v1/backoffice/students_registration")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    /**
     *
     * @param idStudent - return a student by id.
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/student/{id_student}")
    public ResponseEntity<EnvelopedData<StudentModel>>
        getStudentByIdStudent(@PathVariable(name="id_student") Long idStudent) {
        return ResponseEntity.ok(new EnvelopedData<StudentModel>(this.studentService.getStudentByIdStudent(idStudent)));
    }
}
