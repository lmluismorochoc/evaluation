package com.nttdata.accountService.application.dto.report;

import com.nttdata.accountService.application.dto.client.ClientDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResReportDto {
    ClientDto client;
    List<ResAccountReportDTO> accounts;
}
