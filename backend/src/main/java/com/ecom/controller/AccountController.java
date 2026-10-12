package com.ecom.controller;

import com.ecom.dto.request.ActiveAccountRequest;
import com.ecom.dto.request.LoginRequest;
import com.ecom.dto.request.RegisterRequest;
import com.ecom.dto.request.ResetPasswordRequest;
import com.ecom.dto.response.ApiResponse;
import com.ecom.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/Account")
public class AccountController {

    private final AuthService authService;

    @Value("${jwt.cookie-name:token}")
    private String cookieName;

    public AccountController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/Register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest registerDTO) {
        String result = authService.register(registerDTO);
        return ResponseEntity.ok(new ApiResponse(200, result));
    }

    @PostMapping("/Login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginDTO, HttpServletResponse response) {
        String token = authService.login(loginDTO);

        ResponseCookie cookie = ResponseCookie.from(cookieName, token)
                .httpOnly(true)
                .secure(false) // allows local HTTP testing with Angular
                .path("/")
                .maxAge(86400) // 24 hours
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(Map.of(
                "statusCode", 200,
                "message", "Login successful",
                "token", token
        ));
    }

    @PostMapping("/Active-account")
    public ResponseEntity<ApiResponse> activeAccount(@Valid @RequestBody ActiveAccountRequest activeAccountDTO) {
        boolean result = authService.activeAccount(activeAccountDTO);
        if (!result) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "Invalid request"));
        }
        return ResponseEntity.ok(new ApiResponse(200, "Account activated successfully"));
    }

    @PostMapping("/SendEmail-ForForgetPassword")
    public ResponseEntity<ApiResponse> sendEmailForForgetPassword(@RequestParam String email) {
        boolean result = authService.sendEmailForForgetPassword(email);
        if (!result) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "Invalid request"));
        }
        return ResponseEntity.ok(new ApiResponse(200, "Email sent successfully"));
    }

    @PostMapping("/Reset-Password")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordDTO) {
        String result = authService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok(new ApiResponse(200, result));
    }

    @PostMapping("/Logout")
    public ResponseEntity<ApiResponse> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(cookieName, "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(new ApiResponse(200, "Logged out successfully"));
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }
        return ResponseEntity.ok(Map.of(
                "authenticated", true,
                "email", userDetails.getUsername(),
                "authorities", userDetails.getAuthorities()
        ));
    }
}
