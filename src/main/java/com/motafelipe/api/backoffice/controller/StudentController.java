package com.motafelipe.api.backoffice.controller;

import com.motafelipe.api.backoffice.domains.vo.entities.StudentEntity;
import com.motafelipe.api.backoffice.models.EnvelopedData;
import com.motafelipe.api.backoffice.models.pagination.PageModel;
import com.motafelipe.api.backoffice.models.pagination.PageRequestModel;
import com.motafelipe.api.backoffice.models.students.StudentModel;
import com.motafelipe.api.backoffice.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javassist.NotFoundException;

@RestController
@RequestMapping(value="/v1/backoffice/students_registration")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }


    @PutMapping("/student/{id_student}")
    public ResponseEntity<StudentModel> update(@PathVariable(name="id_student") Long idStudent, @RequestBody StudentModel studentModel){
        studentModel.setIdStudent(idStudent);
        StudentModel updatedStudentModel = this.studentService.update(studentModel);
        return ResponseEntity.ok(updatedStudentModel);
    }

    /**
     * Returns a student by id.
     * @param idStudent - identification of a single student.
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/student/{id_student}")
    public ResponseEntity<EnvelopedData<StudentModel>> getById(@PathVariable(name="id_student") Long idStudent) {
        return ResponseEntity.ok(new EnvelopedData<>(this.studentService.getById(idStudent)));
    }

    /**
     * Delete a student by id.
     * @param idStudent - identification of a single student.
     * @return
     * @throws NotFoundException
     */
    @DeleteMapping("/student/{id_student}")
    public ResponseEntity deleteById(@PathVariable(name="id_student") Long idStudent) {
        this.studentService.deleteById(idStudent);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Create a new resource.
     * @param studentModel
     * @return
     */
    @PostMapping("/student")
    public ResponseEntity<EnvelopedData<StudentModel>> save (@RequestBody StudentModel studentModel){
        var result = this.studentService.save(studentModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EnvelopedData<>(result));
    }

    /**
     * Get all resources by lazy loading.
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public ResponseEntity<PageModel<StudentEntity>> getPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageRequestModel pr = new PageRequestModel(page, size);
        PageModel<StudentEntity> pm = this.studentService.getPagination(pr);
        return ResponseEntity.ok(pm);
    }
}
