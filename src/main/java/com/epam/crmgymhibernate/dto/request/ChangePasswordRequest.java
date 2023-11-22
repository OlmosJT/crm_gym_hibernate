package com.epam.crmgymhibernate.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record ChangePasswordRequest(
        @NotNull @NotEmpty
        String username,
        @NotNull @NotEmpty
        String oldPassword,
        @NotNull @NotEmpty
        String newPassword
) {
}
