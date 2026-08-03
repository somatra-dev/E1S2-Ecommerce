package co.istad.matra.ecommerce.features.userprofile.service;


import co.istad.matra.ecommerce.features.userprofile.dto.PatchUserProfileRequest;
import co.istad.matra.ecommerce.features.userprofile.dto.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse getUserProfile();

    UserProfileResponse patchUserProfile(PatchUserProfileRequest patchUserProfileRequest);

}