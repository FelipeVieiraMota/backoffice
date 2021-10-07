package com.motafelipe.api.backoffice.services;

import com.motafelipe.api.backoffice.domains.vo.entities.StudentEntity;
import com.motafelipe.api.backoffice.models.StudentModel;
import com.motafelipe.api.backoffice.repositories.StudentRepository;
import com.motafelipe.api.backoffice.util.Util;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.motafelipe.api.backoffice.util.BundleEnum.STUDENT_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public StudentModel getStudentByIdStudent(Long idStudent){

        Optional<StudentEntity> data =  this.studentRepository.getStudentByIdStudent(idStudent);

        data.orElseThrow(
            () -> new ResponseStatusException(NOT_FOUND, Util.getMessage(STUDENT_NOT_FOUND.name(), idStudent))
        );

        return StudentModel.toModel(data.get());
    }
}
