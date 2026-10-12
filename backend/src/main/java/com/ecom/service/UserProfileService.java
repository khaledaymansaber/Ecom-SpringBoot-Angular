package com.ecom.service;

import com.ecom.dto.request.AddressRequest;
import com.ecom.dto.request.UpdateProfileRequest;
import com.ecom.dto.response.UserProfileResponse;
import com.ecom.entity.Address;
import com.ecom.entity.AppUser;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.repository.AddressRepository;
import com.ecom.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserProfileService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserProfileService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(String email) {
        AppUser user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return new UserProfileResponse(
                user.getId(),
                user.getDisplayName(),
                user.getUserName(),
                user.getEmail(),
                user.getAddress()
        );
    }

    public UserProfileResponse updateProfile(String email, UpdateProfileRequest request) {
        AppUser user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        user.setDisplayName(request.getDisplayName());
        AppUser saved = userRepository.save(user);

        return new UserProfileResponse(
                saved.getId(),
                saved.getDisplayName(),
                saved.getUserName(),
                saved.getEmail(),
                saved.getAddress()
        );
    }

    @Transactional(readOnly = true)
    public Address getUserAddress(String email) {
        AppUser user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getAddress();
    }

    public Address updateAddress(String email, AddressRequest request) {
        AppUser user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
            address.setUser(user);
        }

        address.setFirstName(request.getFirstName());
        address.setLastName(request.getLastName());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());

        user.setAddress(address);
        userRepository.save(user);

        return address;
    }
}
