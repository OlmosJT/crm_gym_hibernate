package com.epam.crmgymhibernate.dto.request;

public record ChangePasswordRequest(
        String username,
        String oldPassword,
        String newPassword
) {
}
