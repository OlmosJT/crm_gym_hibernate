package com.epam.crmgymhibernate.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record UpdateTraineeProfileRequest(
        @NotNull @NotEmpty
        String username,
        @NotNull @NotEmpty
        String firstname,
        @NotNull @NotEmpty
        String lastname,
        @NotNull @NotEmpty
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
        LocalDate dateOfBirth,
        @NotNull @NotEmpty
        String address,
        boolean isActive
) {
    public UpdateTraineeProfileRequest(String username, String firstname, String lastname, LocalDate dateOfBirth, String address, boolean isActive) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.isActive = isActive;
    }

    public UpdateTraineeProfileRequest(String username, String firstname, String lastname, boolean isActive) {
        this(username, firstname, lastname, null, null, isActive);
    }
}
