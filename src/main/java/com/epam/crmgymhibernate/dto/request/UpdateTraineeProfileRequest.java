package com.epam.crmgymhibernate.dto.request;

import java.time.LocalDate;

public record UpdateTraineeProfileRequest(
        String username,
        String firstname,
        String lastname,
        LocalDate dateOfBirth,
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
