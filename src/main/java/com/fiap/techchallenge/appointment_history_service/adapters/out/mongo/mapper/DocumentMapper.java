package com.fiap.techchallenge.appointment_history_service.adapters.out.mongo.mapper;

import com.fiap.techchallenge.appointment_history_service.adapters.out.mongo.document.*;
import com.fiap.techchallenge.appointment_history_service.domain.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    AppointmentHistoryDocument toDocument(AppointmentHistoryDomain domain);

    DoctorDocument toDoctorDocument(DoctorDomain doctor);

    PatientDocument toPatientDocument(PatientDomain patient);

    NurseDocument toNurseDocument(NurseDomain nurse);

    AddressDocument toAddressDocument(AddressDomain address);

    SpecialityDocument toSpecialityDocument(SpecialityDomain speciality);

    AppointmentHistoryDomain toAppointmentHistoryDomain(AppointmentHistoryDocument doctor);

    DoctorDomain toDoctorDomain(DoctorDocument doctor);

    PatientDomain toPatientDomain(PatientDocument patient);

    NurseDomain toNurseDomain(NurseDocument nurse);

    AddressDomain toAddressDomain(AddressDocument address);

    SpecialityDomain toSpecialityDomain(SpecialityDomain speciality);
}
