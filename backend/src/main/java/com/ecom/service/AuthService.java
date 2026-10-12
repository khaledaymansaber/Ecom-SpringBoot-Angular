package com.ecom.service;

import com.ecom.dto.request.ActiveAccountRequest;
import com.ecom.dto.request.LoginRequest;
import com.ecom.dto.request.RegisterRequest;
import com.ecom.dto.request.ResetPasswordRequest;
import com.ecom.entity.AppUser;
import com.ecom.entity.enums.Role;
import com.ecom.exception.BadRequestException;
import com.ecom.repository.UserRepository;
import com.ecom.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final EmailService emailService;

    @Value("${app.frontend-url:http://localhost:4200}")
    private String frontendUrl;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider tokenProvider,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.emailService = emailService;
    }

    public String register(RegisterRequest request) {
        String cleanEmail = request.getEmail() != null ? request.getEmail().trim().toLowerCase() : "";
        String cleanUserName = request.getUserName() != null ? request.getUserName().trim() : "";

        if (userRepository.existsByUserNameIgnoreCase(cleanUserName)) {
            throw new BadRequestException("this UserName is already registered");
        }
        if (userRepository.existsByEmailIgnoreCase(cleanEmail)) {
            throw new BadRequestException("this email is already registered");
        }

        AppUser user = new AppUser();
        user.setUserName(cleanUserName);
        user.setDisplayName(request.getDisplayName() != null ? request.getDisplayName().trim() : cleanUserName);
        user.setEmail(cleanEmail);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        String confirmationToken = UUID.randomUUID().toString();
        user.setConfirmationToken(confirmationToken);
        user.setEmailConfirmed(false);

        userRepository.save(user);

        // Send activation email
        emailService.sendAccountActivationEmail(user.getEmail(), confirmationToken, frontendUrl);

        return "User registered successfully";
    }

    public String login(LoginRequest request) {
        String cleanEmail = request.getEmail() != null ? request.getEmail().trim() : "";
        AppUser user = userRepository.findByEmailIgnoreCase(cleanEmail)
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        if (!user.isEmailConfirmed()) {
            String token = UUID.randomUUID().toString();
            user.setConfirmationToken(token);
            userRepository.save(user);
            emailService.sendAccountActivationEmail(user.getEmail(), token, frontendUrl);
            throw new BadRequestException("Please confirm your email first,we have send active to your E-mail.");
        }

        return tokenProvider.generateToken(user.getEmail(), user.getUserName());
    }

    public boolean activeAccount(ActiveAccountRequest request) {
        String cleanEmail = request.getEmail() != null ? request.getEmail().trim() : "";
        AppUser user = userRepository.findByEmailIgnoreCase(cleanEmail)
                .orElseThrow(() -> new BadRequestException("Invalid request"));

        if (request.getToken() != null && request.getToken().equals(user.getConfirmationToken())) {
            user.setEmailConfirmed(true);
            user.setConfirmationToken(null);
            userRepository.save(user);
            return true;
        }

        // Token mismatch - generate new one and resend
        String newToken = UUID.randomUUID().toString();
        user.setConfirmationToken(newToken);
        userRepository.save(user);
        emailService.sendAccountActivationEmail(user.getEmail(), newToken, frontendUrl);
        return false;
    }

    public boolean sendEmailForForgetPassword(String email) {
        String cleanEmail = email != null ? email.trim() : "";
        return userRepository.findByEmailIgnoreCase(cleanEmail).map(user -> {
            String token = UUID.randomUUID().toString();
            user.setResetPasswordToken(token);
            userRepository.save(user);
            emailService.sendPasswordResetEmail(user.getEmail(), token, frontendUrl);
            return true;
        }).orElse(false);
    }

    public String resetPassword(ResetPasswordRequest request) {
        String cleanEmail = request.getEmail() != null ? request.getEmail().trim() : "";
        AppUser user = userRepository.findByEmailIgnoreCase(cleanEmail)
                .orElseThrow(() -> new BadRequestException("Invalid request or user not found"));

        if (request.getToken() != null && request.getToken().equals(user.getResetPasswordToken())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setResetPasswordToken(null);
            userRepository.save(user);
            return "password change success";
        }

        throw new BadRequestException("Invalid or expired token");
    }
}
