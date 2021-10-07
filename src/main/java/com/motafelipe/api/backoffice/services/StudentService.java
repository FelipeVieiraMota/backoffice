package com.motafelipe.api.backoffice.services;

import com.motafelipe.api.backoffice.domains.vo.entities.StudentEntity;
import com.motafelipe.api.backoffice.exception.NotFoundException;
import com.motafelipe.api.backoffice.models.PageModel;
import com.motafelipe.api.backoffice.models.PageRequestModel;
import com.motafelipe.api.backoffice.models.StudentModel;
import com.motafelipe.api.backoffice.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public StudentModel save(StudentModel studentModel){

        studentModel.setToken(UUID.randomUUID().toString());
        studentModel.setRa(UUID.randomUUID().toString());
        studentModel.setStudentInternalCode(UUID.randomUUID().toString());
        studentModel.setCreationDate(new Date());

        var data = Optional.of(this.studentRepository.save(StudentModel.toEntity(studentModel)));

        data.orElseThrow(
            () -> new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, " We cannot to save " + studentModel.getFirstName() + " in our database, please try it again in a few moment.")
        );

        return StudentModel.toModel(data.get());
    }

    /**
     * @param studentModel - Student model object.
     * @return StudentModel
     */
    public StudentModel update(StudentModel studentModel){

        var result =
                this.studentRepository
                .getStudentByIdStudent(studentModel.getIdStudent())
                .map(
                    resource -> {
                        resource.setEmail(studentModel.getEmail() == null ? resource.getEmail() : studentModel.getEmail());
                        resource.setCreationDate(studentModel.getCreationDate() == null ? resource.getCreationDate() : studentModel.getCreationDate());
                        resource.setToken(studentModel.getToken() == null ? resource.getToken() : studentModel.getToken());
                        resource.setRa(studentModel.getRa() == null ? resource.getRa() : studentModel.getRa());
                        resource.setStudentInternalCode(studentModel.getStudentInternalCode() == null ? resource.getStudentInternalCode() : studentModel.getStudentInternalCode());
                        resource.setIdStudent(studentModel.getIdStudent() == null ? resource.getIdStudent() : studentModel.getIdStudent());
                        resource.setAge(studentModel.getAge() == 0 ? resource.getAge() : studentModel.getAge());
                        resource.setCpf(studentModel.getCpf() == null ? resource.getCpf() : studentModel.getCpf());
                        resource.setRg(studentModel.getRg() == null ? resource.getRg() : studentModel.getRg());
                        resource.setCellphone(studentModel.getCellphone() == null ? resource.getCellphone() : studentModel.getCellphone());
                        return this.studentRepository.save(resource);
                    }
                )
                .orElseThrow(
                    () ->
                    new HttpServerErrorException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        " We cannot to save " + studentModel.getFirstName() + " in our database, please try it again in a few moment."
                    )
                );

        return StudentModel.toModel(result);
    }

    public void deleteById(Long idStudent){
        this.studentRepository.deleteById(idStudent);
    }

    public StudentModel getById(Long idStudent){

        Optional<StudentEntity> data =  this.studentRepository.getStudentByIdStudent(idStudent);

        data.orElseThrow(
            () -> new NotFoundException("There are not student with id = " + idStudent)
        );

        return StudentModel.toModel(data.get());
    }

    public PageModel<StudentEntity> getPagination (PageRequestModel pr) {

        Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());

        Page<StudentEntity> page = studentRepository.findAll(pageable);

        return new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
    }
}
