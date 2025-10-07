package com.fiap.techchallenge.appointment_history_service.adapters.in.graphql;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;
import com.fiap.techchallenge.appointment_history_service.application.usecase.ReadAppointmentByIdCase;
import com.fiap.techchallenge.appointment_history_service.application.usecase.ReadAppointmentsByDoctorAndDateCase;
import com.fiap.techchallenge.appointment_history_service.application.usecase.ReadAppointmentsByPatientAndDateCase;
import com.fiap.techchallenge.appointment_history_service.application.usecase.UpdateAppointmentHistoryCase;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppointmentHistoryController {

    private final ReadAppointmentByIdCase readAppointmentHistoryCase;
    private final UpdateAppointmentHistoryCase updateAppointmentHistoryCase;
    private final ReadAppointmentsByDoctorAndDateCase readAppointmentsByDoctorAndDateCase;
    private final ReadAppointmentsByPatientAndDateCase readAppointmentsByPatientAndDateCase;

    @QueryMapping
    public AppointmentHistoryResponse appointmentHistoryById(
            @Argument String appointmentId,
            @ContextValue("authToken") String authToken
    ) {
        return readAppointmentHistoryCase.getById(appointmentId, authToken);
    }

    @MutationMapping
    public AppointmentHistoryResponse updateObservationHistoryById(
            @Argument String appointmentId,
            @Argument String observation,
            @ContextValue("authToken") String authToken
    ) {
        return updateAppointmentHistoryCase.updateById(appointmentId, observation, authToken);
    }

    @QueryMapping
    public List<AppointmentHistoryResponse> appointmentsByDoctorAndDate(
            @Argument String doctorId,
            @Argument String startDate,
            @Argument String endDate,
            @ContextValue("authToken") String authToken
    ) {
        var parsedStartDate = java.time.LocalDate.parse(startDate);
        var parsedEndDate = java.time.LocalDate.parse(endDate);
        return readAppointmentsByDoctorAndDateCase.getByDoctorAndDateCase(doctorId, parsedStartDate, parsedEndDate, authToken);
    }

    @QueryMapping
    public List<AppointmentHistoryResponse> appointmentsByPatientAndDate(
            @Argument String patientId,
            @Argument String startDate,
            @Argument String endDate,
            @ContextValue("authToken") String authToken
    ) {
        var parsedStartDate = java.time.LocalDate.parse(startDate);
        var parsedEndDate = java.time.LocalDate.parse(endDate);
        return readAppointmentsByPatientAndDateCase.getByPatientAndDateCase(patientId, parsedStartDate, parsedEndDate, authToken);
    }
}
