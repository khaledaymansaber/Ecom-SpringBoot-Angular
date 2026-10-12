package com.ecom.controller;

import com.ecom.dto.request.AddressRequest;
import com.ecom.dto.request.UpdateProfileRequest;
import com.ecom.dto.response.UserProfileResponse;
import com.ecom.entity.Address;
import com.ecom.security.UserPrincipal;
import com.ecom.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Profile")
public class ProfileController {

    private final UserProfileService profileService;

    public ProfileController(UserProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(profileService.getProfile(userPrincipal.getEmail()));
    }

    @PutMapping
    public ResponseEntity<UserProfileResponse> updateProfile(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateProfile(userPrincipal.getEmail(), request));
    }

    @GetMapping("/address")
    public ResponseEntity<Address> getAddress(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(profileService.getUserAddress(userPrincipal.getEmail()));
    }

    @PutMapping("/address")
    public ResponseEntity<Address> updateAddress(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(profileService.updateAddress(userPrincipal.getEmail(), request));
    }
}
