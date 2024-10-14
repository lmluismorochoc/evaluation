package com.nttdata.accountService.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@Table(name = "MOVEMENTS")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovementsEntity {
    @Id
    Long id;
    @Column(value = "MOVEMENT_TYPE")
    String movementType;
    Double balance;
    Double amount;
    Date date;
    @Column(value = "ACCOUNT_ID")
    Long accountId;

}
