package com.fiap.techchallenge.appointment_history_service.application.mapper;

import com.fiap.techchallenge.appointment_history_service.application.dto.AppointmentEventDto;
import com.fiap.techchallenge.appointment_history_service.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EventToDomainMapper {

    @Mapping(target = "appointmentId", source = "appointmentId")
    @Mapping(target = "scheduledAt", expression = "java(dto.dateTime().atZone(java.time.ZoneId.of(\"America/Sao_Paulo\")).toInstant())")
    @Mapping(target = "createdAt", expression = "java(dto.createdAt().atZone(java.time.ZoneId.of(\"America/Sao_Paulo\")).toInstant())")
    @Mapping(target = "ingestedAt", expression = "java(java.time.Instant.now())")
    AppointmentHistoryDomain toDomain(AppointmentEventDto dto);

    DoctorDomain toDoctorDomain(AppointmentEventDto.PersonDoctor doctor);

    PatientDomain toPatientDomain(AppointmentEventDto.PersonPatient patient);

    NurseDomain toNurseDomain(AppointmentEventDto.PersonNurse nurse);

    AddressDomain toAAddressDomain(AppointmentEventDto.Address address);

    SpecialityDomain toSpecialityDomain(AppointmentEventDto.Speciality speciality);

}
