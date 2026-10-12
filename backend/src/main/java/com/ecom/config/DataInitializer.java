package com.ecom.config;

import com.ecom.entity.Address;
import com.ecom.entity.AppUser;
import com.ecom.entity.Category;
import com.ecom.entity.DeliveryMethod;
import com.ecom.entity.Photo;
import com.ecom.entity.Product;
import com.ecom.entity.enums.Role;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.DeliveryMethodRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           DeliveryMethodRepository deliveryMethodRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.deliveryMethodRepository = deliveryMethodRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedDeliveryMethods();
        seedCategoriesAndProducts();
        seedUsers();
    }

    private void seedDeliveryMethods() {
        if (deliveryMethodRepository.count() == 0) {
            deliveryMethodRepository.saveAll(List.of(
                    new DeliveryMethod("Free Shipping", BigDecimal.ZERO, "Standard free delivery on all eligible orders", "5-7 Business Days"),
                    new DeliveryMethod("Standard Delivery", new BigDecimal("5.00"), "Reliable standard postal delivery", "3-5 Business Days"),
                    new DeliveryMethod("Express Courier", new BigDecimal("15.00"), "Fast express courier delivery", "1-2 Business Days"),
                    new DeliveryMethod("Next Day Delivery", new BigDecimal("25.00"), "Guaranteed priority next-day arrival", "24 Hours")
            ));
            logger.info("Seeded delivery methods");
        }
    }

    private void seedCategoriesAndProducts() {
        if (categoryRepository.count() == 0) {
            Category laptops = categoryRepository.save(new Category("Laptops", "High performance gaming, business and ultrabook laptops"));
            Category smartphones = categoryRepository.save(new Category("Smartphones", "Modern mobile devices, tablets and wearables"));
            Category audio = categoryRepository.save(new Category("Audio", "Premium wireless headphones, earbuds and speakers"));
            Category accessories = categoryRepository.save(new Category("Accessories", "Mice, keyboards, docking stations and computer peripherals"));

            logger.info("Seeded categories");

            // Seed initial products
            Product p1 = new Product(
                    "ASUS TUF Gaming A15",
                    "AMD Ryzen 7 7735HS, NVIDIA GeForce RTX 4060, 16GB DDR5 RAM, 512GB PCIe SSD, 15.6 FHD 144Hz display.",
                    new BigDecimal("1299.99"),
                    new BigDecimal("1099.99"),
                    laptops
            );
            p1.addPhoto(new Photo("asus tuf/zu352_1.jpg", p1));
            productRepository.save(p1);

            Product p2 = new Product(
                    "Apple MacBook Air M3",
                    "13.6-inch Liquid Retina Display, 8-Core CPU, 10-Core GPU, 16GB Unified Memory, 512GB SSD Storage.",
                    new BigDecimal("1399.00"),
                    new BigDecimal("1249.00"),
                    laptops
            );
            p2.addPhoto(new Photo("Laptop/macbook.jpg", p2));
            productRepository.save(p2);

            Product p3 = new Product(
                    "Sony WH-1000XM5 Wireless Headphones",
                    "Industry Leading Noise Canceling Wireless Headphones with Auto NC Optimizer, Hands-Free Calling and Alexa Voice Control.",
                    new BigDecimal("399.99"),
                    new BigDecimal("329.99"),
                    audio
            );
            p3.addPhoto(new Photo("Audio/sony-xm5.jpg", p3));
            productRepository.save(p3);

            Product p4 = new Product(
                    "Logitech MX Master 3S Wireless Mouse",
                    "Performance wireless mouse with 8K DPI any-surface tracking, quiet clicks, and ultra-fast electromagnetic scrolling.",
                    new BigDecimal("119.99"),
                    new BigDecimal("99.99"),
                    accessories
            );
            p4.addPhoto(new Photo("Accessories/mx-master-3s.jpg", p4));
            productRepository.save(p4);

            Product p5 = new Product(
                    "Samsung Galaxy S24 Ultra",
                    "Galaxy AI Smartphone with 200MP Camera, S Pen included, Snapdragon 8 Gen 3, Titanium Gray 256GB.",
                    new BigDecimal("1299.99"),
                    new BigDecimal("1149.99"),
                    smartphones
            );
            p5.addPhoto(new Photo("Smartphones/s24-ultra.jpg", p5));
            productRepository.save(p5);

            logger.info("Seeded sample products with categories and photos");
        }
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            // Admin user
            AppUser admin = new AppUser("System Admin", "admin", "admin@ecom.com", passwordEncoder.encode("Admin@123456"));
            admin.setRole(Role.ROLE_ADMIN);
            admin.setEmailConfirmed(true);
            userRepository.save(admin);

            // Demo user with address
            AppUser demoUser = new AppUser("John Doe", "johndoe", "user@ecom.com", passwordEncoder.encode("User@123456"));
            demoUser.setRole(Role.ROLE_USER);
            demoUser.setEmailConfirmed(true);

            Address demoAddress = new Address("John", "Doe", "123 Main Street", "New York", "NY", "10001");
            demoUser.setAddress(demoAddress);
            userRepository.save(demoUser);

            logger.info("Seeded admin and demo user accounts");
        }
    }
}
