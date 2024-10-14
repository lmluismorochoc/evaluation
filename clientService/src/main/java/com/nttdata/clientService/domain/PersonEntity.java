package com.nttdata.clientService.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "PERSONS")
public class PersonEntity {

    @Id
    Long id;

    @Column
    String name;
    @Column
    String gender;
    @Column
    Integer age;
    @Column
    String identification;
    @Column
    String direction;
    @Column
    String phone;



}
