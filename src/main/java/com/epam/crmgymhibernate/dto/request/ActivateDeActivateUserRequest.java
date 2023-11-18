package com.epam.crmgymhibernate.dto.request;

public record ActivateDeActivateUserRequest(
        String username,
        boolean isActive
) {
}
