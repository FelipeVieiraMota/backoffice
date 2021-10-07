package com.motafelipe.api.backoffice.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.motafelipe.api.backoffice.domains.vo.entities.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentModel {

    @JsonProperty(value = "id_student")
    public Long idStudent;

    @JsonProperty(value = "token")
    public String token;

    @JsonProperty(value = "first_name")
    public String firstName;

    @JsonProperty(value = "last_name")
    public String lastName;

    @JsonProperty(value = "cpf")
    public String cpf;

    @JsonProperty(value = "rg")
    public String rg;

    @JsonProperty(value = "age")
    public int age;

    @JsonProperty(value = "date_creation")
    public LocalDate creationDate;

    @JsonProperty(value = "email")
    public String email;

    @JsonProperty(value = "ra")
    public String ra;

    @JsonProperty(value = "student_internal_code")
    public String studentInternalCode;

    @JsonProperty(value = "cellphone")
    public String cellphone;


    public static StudentModel toModel (StudentEntity studentEntity){
        return new StudentModel(
                studentEntity.getIdStudent(),
                studentEntity.getToken(),
                studentEntity.getFirstName(),
                studentEntity.getLastName(),
                studentEntity.getCpf(),
                studentEntity.getRg(),
                studentEntity.getAge(),
                studentEntity.getCreationDate(),
                studentEntity.getEmail(),
                studentEntity.getRa(),
                studentEntity.getStudentInternalCode(),
                studentEntity.getCellphone()
        );
    }
}
