package com.fiap.techchallenge.appointment_history_service.domain.auth;

import com.fiap.techchallenge.appointment_history_service.domain.util.Role;
import lombok.AllArgsConstructor;

import static com.fiap.techchallenge.appointment_history_service.domain.util.Role.*;

@AllArgsConstructor
public class AuthorizationProfileDomain {
    private String userId;
    private Role role;


    public boolean isAllowedToUpdate() {
        return isDoctor();
    }

    public boolean isAllowedToRead(String id) {
        return (isPatient() && userId.equals(id) || isDoctor() || isNurse());
    }

    private boolean isDoctor() {
        return role == DOCTOR;
    }

    private boolean isPatient() {
        return role == PATIENT;
    }

    private boolean isNurse() {
        return role == NURSE;
    }
}
