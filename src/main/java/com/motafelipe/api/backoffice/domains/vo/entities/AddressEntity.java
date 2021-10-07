package com.motafelipe.api.backoffice.domains.vo.entities;

import javax.persistence.*;

@Entity(name = "tb_address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address", nullable = false)
    private Long idAddress;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private StudentEntity fkStudent;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "number")
    private String number;

    @Column(name = "complement")
    private String complement;

    @Column(name = "cep")
    private String cep;

    @Column(name = "neighbor")
    private String neighbor;
}