package com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.mapper;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;
import com.fiap.techchallenge.appointment_history_service.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface DomainToResponseMapper {

    @Mapping(target = "appointmentId", expression = "java(String.valueOf(doctorDomain.getAppointmentId()))")
    @Mapping(target = "createdAt", expression = "java(doctorDomain.getCreatedAt()==null?null:doctorDomain.getCreatedAt().toString())")
    @Mapping(target = "scheduledAt", expression = "java(doctorDomain.getScheduledAt()==null?null:doctorDomain.getScheduledAt().toString())")
    @Mapping(target = "ingestedAt", expression = "java(doctorDomain.getIngestedAt()==null?null:doctorDomain.getIngestedAt().toString())")
    AppointmentHistoryResponse toResponse(AppointmentHistoryDomain doctorDomain);


    AppointmentHistoryResponse.PersonDoctorResponse toDoctorResponse(DoctorDomain doctorDomain);

    AppointmentHistoryResponse.PersonPatientResponse toPatientResponse(PatientDomain patientDomain);

    AppointmentHistoryResponse.PersonNurseResponse toNurseResponse(NurseDomain nurseDomain);

    AppointmentHistoryResponse.SpecialityResponse toSpecialityResponse(SpecialityDomain specialityDomain);

    AppointmentHistoryResponse.AddressResponse toAddressResponse(AddressDomain addressDomain);
}
