package co.istad.matra.ecommerce.features.userprofile.service.impl;


import co.istad.matra.ecommerce.features.userprofile.UserProfile;
import co.istad.matra.ecommerce.features.userprofile.UserProfileRepository;
import co.istad.matra.ecommerce.features.userprofile.dto.PatchUserProfileRequest;
import co.istad.matra.ecommerce.features.userprofile.dto.UserProfileResponse;
import co.istad.matra.ecommerce.features.userprofile.mapper.UserProfileMapper;
import co.istad.matra.ecommerce.features.userprofile.service.UserProfileService;
import co.istad.matra.ecommerce.security.keycloak.KeycloakProperties;
import co.istad.matra.ecommerce.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProps;

    @Override
    public UserProfileResponse getUserProfile() {
        // Get user profile from keycloak
        String userId = AuthUtil.extractUserId();
        UserResource userResource = keycloak.realm(keycloakProps.getRealm())
                .users()
                .get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        log.info("user profile: {}", userRepresentation);

        // Get user profile from database
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User profile has not been found"
                ));

        return userProfileMapper.buildUserProfileResponse(userRepresentation, userProfile);
    }

    @Override
    public UserProfileResponse patchUserProfile(PatchUserProfileRequest patchUserProfileRequest) {

        String userId = AuthUtil.extractUserId();

        // Get user profile from database
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User profile has not been found"
                ));
        userProfileMapper.toUserProfileEntity(userProfile, patchUserProfileRequest);
        userProfileRepository.save(userProfile);

        UserResource userResource = keycloak.realm(keycloakProps.getRealm())
                .users()
                .get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userProfileMapper.toUserRepresentation(userRepresentation, patchUserProfileRequest);
        userResource.update(userRepresentation);

        return userProfileMapper.buildUserProfileResponse(userRepresentation, userProfile);
    }

}