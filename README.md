# Enterprise E-Commerce Platform: Java Spring Boot + Angular

Production-ready, full-stack E-Commerce web application migrated from .NET to **Java Spring Boot 3.3.4 (Java 21)** with an **Angular 22** frontend.

---

## 🏗️ Architecture & Technologies

### Backend (Java Spring Boot)
- **Framework:** Spring Boot 3.3.4 (Java 21 LTS)
- **Database:** PostgreSQL (with embedded H2 database dev profile support)
- **Persistence:** Spring Data JPA / Hibernate ORM
- **Security:** Spring Security 6 with stateless JWT authentication
- **Token Management:** JJWT (Java JWT) with HTTP-only cookie support (matching original frontend auth pattern) + Authorization Header fallback
- **File Uploads:** Multipart image storage with UUID-based unique filenames and static resource serving
- **Email:** Spring Mail with HTML templates for email activation and password reset flows
- **API Documentation:** SpringDoc OpenAPI 3 (Swagger UI)

### Frontend (Angular 22)
- **Framework:** Angular 22 with TypeScript 5
- **UI Frameworks:** Bootstrap 5, Font Awesome, Angular Material
- **State Management:** Reactive RXJS (`BehaviorSubject` for Cart, Auth state, and Wishlist badge counter)
- **Interceptors:**
  - `loaderInterceptor`: Centralized loading spinner via `ngx-spinner`
  - `jwtInterceptor`: Automatic `withCredentials: true` and Bearer token attachment
  - `errorInterceptor`: Centralized HTTP error handling with `ngx-toastr` notifications and 401 redirect
- **Route Guards:** Functional `authGuard` protecting checkout, orders, wishlist, and profile routes

---

## 🚀 Completed Modules & Missing Features Solved

| Module | Status | Features Included |
|---|---|---|
| **Product Catalog** | ✅ Completed | Search, Category Filter, Price/Name Sort, Pagination, Multi-photo galleries |
| **Category Management** | ✅ Completed | Category listings, CRUD operations |
| **Shopping Cart** | ✅ Completed | Add to cart, quantity adjust, item removal, subtotal calculation |
| **Checkout Flow** | ✅ Completed | Multi-step checkout, default shipping address auto-population, delivery method selection, live price computation, order submission |
| **Order Management** | ✅ Completed | Order creation, user order history table with status badges, individual order detail view with items breakdown |
| **Wishlist** | ✅ Completed | Add/remove items, live counter badge in navbar, "Move to Cart" one-click action |
| **User Profile & Address** | ✅ Completed | Personal information update, default shipping address storage |
| **Authentication & Security** | ✅ Completed | Registration with token activation, login with HTTP-only cookie JWT, password reset flow, logout |
| **Home Page** | ✅ Completed | Modern hero banner, trust & benefit badges, category showcase, featured products |

---

## 🔑 Default Accounts (Seeded automatically on startup)

1. **System Administrator:**
   - **Email:** `admin@ecom.com`
   - **Password:** `Admin@123456`
   - **Role:** `ROLE_ADMIN`

2. **Demo Customer:**
   - **Email:** `user@ecom.com`
   - **Password:** `User@123456`
   - **Role:** `ROLE_USER`
   - **Pre-configured Address:** 123 Main Street, New York, NY 10001

---

## ⚙️ How to Run

### Prerequisites
- Java 21 JDK (`C:\Users\Compumarts\tools\jdk-21`)
- Maven 3.9+ (`C:\Users\Compumarts\tools\maven`)
- Node.js 18+ and npm

### 1. Running Backend (Spring Boot)
Open terminal in `backend/`:
```bash
# Using PostgreSQL (default profile):
mvn spring-boot:run

# Or using embedded H2 profile (zero DB setup required):
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```
Backend API will start at: `http://localhost:8080/`
Swagger UI Documentation: `http://localhost:8080/swagger-ui.html`

### 2. Running Frontend (Angular)
Open terminal in `frontend/`:
```bash
npm start
```
Frontend application will be available at: `http://localhost:4200/`

---

## 📂 Project Structure

```
Ecom-SpringBoot/
├── backend/
│   ├── pom.xml
│   ├── uploads/images/                 # Static product images
│   └── src/main/java/com/ecom/
│       ├── EcomApplication.java
│       ├── config/                     # Security, CORS, Web, Data Initializer
│       ├── controller/                 # REST Controllers (Account, Products, Categories, Basket, Orders, Wishlist, Profile)
│       ├── dto/                        # Request & Response DTOs
│       ├── entity/                     # JPA Entities
│       ├── exception/                  # Global Exception Handler & Custom Errors
│       ├── repository/                 # Spring Data JPA Repositories
│       ├── security/                   # JWT Provider, Auth Filter, UserDetailsService
│       └── service/                    # Business Logic Services
│
└── frontend/
    ├── package.json
    ├── angular.json
    └── src/app/
        ├── basket/                     # Cart component and service
        ├── checkout/                   # Completed Checkout flow
        ├── core/                       # Nav-bar, Guards, Interceptors, Services
        ├── home/                       # Completed Home showcase
        ├── identity/                   # Login, Register, Activation, Reset Password
        ├── orders/                     # Orders history & detail components
        ├── profile/                    # User Profile and Address component
        ├── shared/                     # Models, pagination, order total components
        ├── shop/                       # Shop catalog & product details
        └── wishlist/                   # Wishlist management component
```
