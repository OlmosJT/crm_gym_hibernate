package com.epam.crmgymhibernate.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record ActivateDeActivateUserRequest(
        @NotEmpty @NotNull
        String username,
        boolean isActive
) {
}
