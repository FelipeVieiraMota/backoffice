package com.motafelipe.api.backoffice.controller;

import com.motafelipe.api.backoffice.domains.vo.entities.StudentEntity;
import com.motafelipe.api.backoffice.models.EnvelopedData;
import com.motafelipe.api.backoffice.models.pagination.PageModel;
import com.motafelipe.api.backoffice.models.pagination.PageRequestModel;
import com.motafelipe.api.backoffice.models.students.AddressModel;
import com.motafelipe.api.backoffice.models.students.StudentModel;
import com.motafelipe.api.backoffice.models.user.UserLoginRequestModel;
import com.motafelipe.api.backoffice.models.user.UserLoginResponseModel;
import com.motafelipe.api.backoffice.security.JwtManager;
import com.motafelipe.api.backoffice.services.AddressService;
import com.motafelipe.api.backoffice.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javassist.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/v1/backoffice/students")
public class StudentController {

    private StudentService studentService;
    private AddressService addressService;
    private AuthenticationManager authManager;
    private JwtManager jwtManager;

    /**
     * StudentController
     * @param studentService - studentService
     * @param addressService - addressService
     * @param authManager - authManager
     * @param jwtManager - jwtManager
     */
    @Autowired
    public StudentController(
            StudentService studentService,
            AddressService addressService,
            AuthenticationManager authManager,
            JwtManager jwtManager) {
        this.studentService = studentService;
        this.addressService = addressService;
        this.authManager = authManager;
        this.jwtManager = jwtManager;
    }

    /**
     * Update
     * @param idStudent - idStudent
     * @param studentModel - studentModel
     * @return ResponseEntity<StudentModel>
     */
    @PutMapping("/{id_student}")
    public ResponseEntity<StudentModel> update(@PathVariable(name="id_student") Long idStudent, @RequestBody @Valid StudentModel studentModel){
        studentModel.setIdStudent(idStudent);
        StudentModel updatedStudentModel = this.studentService.update(studentModel);
        return ResponseEntity.ok(updatedStudentModel);
    }

    /**
     * Returns a student by id.
     * @param idStudent - identification of a single student.
     * @return ResponseEntity<EnvelopedData<StudentModel>>
     * @throws NotFoundException - Http status 404
     */
    @GetMapping("/{id_student}")
    public ResponseEntity<EnvelopedData<StudentModel>> getById(@PathVariable(name="id_student") Long idStudent) {
        return ResponseEntity.ok(new EnvelopedData<>(this.studentService.getById(idStudent)));
    }

    /**
     * Delete a student by id.
     * @param idStudent - identification of a single student.
     * @return ResponseEntity
     * @throws NotFoundException - Http status 404
     */
    @DeleteMapping("/{id_student}")
    public ResponseEntity deleteById(@PathVariable(name="id_student") Long idStudent) {
        this.studentService.deleteById(idStudent);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Create a new resource.
     * @param studentModel - parameter
     * @return ResponseEntity<EnvelopedData<StudentModel>>
     */
    @PostMapping()
    public ResponseEntity<EnvelopedData<StudentModel>> save (@RequestBody @Valid StudentModel studentModel){
        var result = this.studentService.save(studentModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EnvelopedData<>(result));
    }

    /**
     * Get all resources by lazy loading.
     * @param page - page
     * @param size - size of page.
     * @return ResponseEntity<PageModel<StudentEntity>>
     */
    @GetMapping
    public ResponseEntity<PageModel<StudentEntity>> getPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageRequestModel pr = new PageRequestModel(page, size);
        PageModel<StudentEntity> pm = this.studentService.getPagination(pr);
        return ResponseEntity.ok(pm);
    }

    /**
     * Create a new address resource.
     * @param addressModel
     * @return ResponseEntity<EnvelopedData<AddressModel>>
     */
    @PostMapping("/{id_student}/address")
    public ResponseEntity<EnvelopedData<AddressModel>> save (
            @PathVariable(name="id_student") Long idStudent,
            @RequestBody @Valid AddressModel addressModel){
        var result = this.addressService.save(idStudent, addressModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EnvelopedData<>(result));
    }

    /**
     * Login
     * @param user - user
     * @return ResponseEntity<UserLoginResponseModel>
     */
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseModel> login(@RequestBody @Valid UserLoginRequestModel user){

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        Authentication auth =
                authManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(auth);

        org.springframework.security.core.userdetails.User userSpring =
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        String email =
                userSpring.getUsername();

        var roles = userSpring
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(jwtManager.createToken(email, roles));
    }
}