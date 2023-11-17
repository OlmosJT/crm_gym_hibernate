package com.epam.crmgymhibernate.dto.request;

import java.time.LocalDate;

public record RegisterTraineeRequest(
        String firstname,
        String lastname,
        LocalDate dateOfBirth,
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
