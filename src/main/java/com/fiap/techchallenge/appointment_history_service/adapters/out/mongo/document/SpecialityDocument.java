package com.fiap.techchallenge.appointment_history_service.adapters.out.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialityDocument {
    private Integer id;
    private String name;
}
