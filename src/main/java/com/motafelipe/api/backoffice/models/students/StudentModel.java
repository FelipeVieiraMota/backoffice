package com.motafelipe.api.backoffice.models.students;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.motafelipe.api.backoffice.domains.vo.entities.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
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

    @JsonProperty(value = "date_of_birth")
    @DateTimeFormat
    @NotNull(message = " Must need have a valid date field. ")
    @NotEmpty(message = " Must need have a valid date field. ")
    @NotBlank(message = " Must need have a valid date field. ")
    public Date dateOfBirth;

    @JsonProperty(value = "date_creation")
    public Date creationDate;

    @JsonProperty(value = "email")
    public String email;

    @JsonProperty(value = "ra")
    public String ra;

    @JsonProperty(value = "student_internal_code")
    public String studentInternalCode;

    @JsonProperty(value = "cellphone")
    public String cellphone;

    @JsonProperty(value = "address")
    public List<AddressModel> address;

    public static StudentModel toModel (StudentEntity studentEntity){
        return new StudentModel(
                studentEntity.getIdStudent(),
                studentEntity.getToken(),
                studentEntity.getFirstName(),
                studentEntity.getLastName(),
                studentEntity.getCpf(),
                studentEntity.getRg(),
                studentEntity.getDateOfBirth(),
                studentEntity.getCreationDate(),
                studentEntity.getEmail(),
                studentEntity.getRa(),
                studentEntity.getStudentInternalCode(),
                studentEntity.getCellphone(),
                AddressModel.toModelList(studentEntity.getAddress())
        );
    }

    public static StudentEntity toEntity(StudentModel studentModel) {
        return new StudentEntity(
                studentModel.getIdStudent(),
                studentModel.getToken(),
                studentModel.getFirstName(),
                studentModel.getLastName(),
                studentModel.getCpf(),
                studentModel.getRg(),
                studentModel.getDateOfBirth(),
                studentModel.getCreationDate(),
                studentModel.getEmail(),
                studentModel.getRa(),
                studentModel.getStudentInternalCode(),
                studentModel.getCellphone(),
                AddressModel.toEntityList(studentModel.getAddress())
        );
    }
}
