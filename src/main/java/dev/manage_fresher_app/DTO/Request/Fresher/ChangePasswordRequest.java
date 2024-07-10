package dev.manage_fresher_app.DTO.Request.Fresher;

import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Current password must not be blank")
    private String currentPassword;

    @NotBlank(message = "New password must not be blank")
    @Size(min = 8, message = "New password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "New password must contain at least one uppercase letter, one lowercase letter, and one digit")
    private String newPassword;

    @NotBlank(message = "Current password must not be blank")
    private String confirmPassword;

}
