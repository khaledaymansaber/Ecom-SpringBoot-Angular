package com.ecom.service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:k8340896@gmail.com}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            logger.info("Email sent successfully to {}", to);
        } catch (Exception ex) {
            logger.warn("Failed to send email to {}: {}. Logging email content instead:\nSubject: {}\nContent: {}",
                    to, ex.getMessage(), subject, htmlContent);
        }
    }

    public void sendAccountActivationEmail(String email, String token, String frontendUrl) {
        String activationUrl = frontendUrl + "/Account/active?email=" + email + "&token=" + token;
        String html = """
                <div style="font-family: Arial, sans-serif; padding: 20px; max-width: 600px; margin: 0 auto; border: 1px solid #e0e0e0; border-radius: 8px;">
                    <h2 style="color: #2b3a4a; text-align: center;">Welcome to E-Commerce!</h2>
                    <p>Thank you for registering. Please confirm your email address by clicking the button below:</p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" style="background-color: #007bff; color: white; padding: 12px 24px; text-decoration: none; border-radius: 4px; font-weight: bold;">Activate Account</a>
                    </div>
                    <p style="color: #666; font-size: 13px;">Or copy and paste this link in your browser:<br/><a href="%s">%s</a></p>
                </div>
                """.formatted(activationUrl, activationUrl, activationUrl);

        sendEmail(email, "Activate Your E-Commerce Account", html);
    }

    public void sendPasswordResetEmail(String email, String token, String frontendUrl) {
        String resetUrl = frontendUrl + "/Account/reset-password?email=" + email + "&token=" + token;
        String html = """
                <div style="font-family: Arial, sans-serif; padding: 20px; max-width: 600px; margin: 0 auto; border: 1px solid #e0e0e0; border-radius: 8px;">
                    <h2 style="color: #2b3a4a; text-align: center;">Reset Your Password</h2>
                    <p>You requested to reset your password. Click the button below to set a new password:</p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" style="background-color: #dc3545; color: white; padding: 12px 24px; text-decoration: none; border-radius: 4px; font-weight: bold;">Reset Password</a>
                    </div>
                    <p style="color: #666; font-size: 13px;">Or copy and paste this link in your browser:<br/><a href="%s">%s</a></p>
                </div>
                """.formatted(resetUrl, resetUrl, resetUrl);

        sendEmail(email, "Reset Your E-Commerce Password", html);
    }
}
