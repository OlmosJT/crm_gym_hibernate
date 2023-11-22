package com.epam.crmgymhibernate.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record RegisterTraineeRequest(
        @NotNull @NotEmpty
        String firstname,
        @NotNull @NotEmpty
        String lastname,
        @NotNull @NotEmpty
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
        LocalDate dateOfBirth,
        @NotNull @NotEmpty
        String address
) {

    public RegisterTraineeRequest(String firstname, String lastname, LocalDate dateOfBirth, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public RegisterTraineeRequest(String firstname, String lastname) {
        this(firstname, lastname, null, null);
    }
}
