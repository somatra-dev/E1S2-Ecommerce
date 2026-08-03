package co.istad.matra.ecommerce.features.userprofile.dto;


import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PatchUserProfileRequest(

        @Size(max = 255)
        String firstName,
        @Size(max = 255)
        String lastName,
        @Size(max = 10)
        String gender,
        @Size(max = 255)
        String biography,
        String profilePicture,
        String jobTitle,
        BigDecimal salary,
        String phoneNumber,
        String githubLink,
        String facebookLink
) {
}
