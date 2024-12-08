# Travel Booking Website

A full-stack travel booking platform built using React, Spring Boot, and MySQL. This application allows users to browse travel packages, make bookings, and includes an admin panel for package management.

## Team Members
- Neel
- Abdul
- Hitesh

## Features

### User Features
- User Registration and Login with JWT Authentication
- Browse Available Travel Packages
- View Package Details
- Add Packages to Cart
- Book Travel Packages
- View Booking History

### Admin Features
- Secure Admin Login with JWT Authentication
- Create New Travel Packages
- Edit Existing Packages
- Delete Packages
- Manage User Bookings

## Technology Stack

### Frontend
- React.js
- Node.js
- NPM (Node Package Manager)
- HTML5/CSS3
- Bootstrap/Material UI

### Backend
- Spring Boot
- Spring Security with JWT Authentication
- Spring Data JPA
- MySQL Database
- BCrypt Password Encryption

## Prerequisites

Before running this application, make sure you have the following installed:
- Node.js (v14 or higher)
- Java JDK (version 11 or higher)
- MySQL Server
- Maven

## Setup Instructions

### Database Setup
1. Create a MySQL database named `travel_booking`
2. Update database configurations in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/travel_booking
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd travel-booking-backend
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   The backend server will start on `http://localhost:8080`

### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd travel-booking-frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm start
   ```
   The frontend application will start on `http://localhost:5173`

## Security Configuration

The application uses JWT (JSON Web Token) for authentication and authorization:

### JWT Implementation
- Token-based authentication for all secured endpoints
- Stateless session management
- BCrypt password encoding
- Role-based access control (USER and ADMIN roles)

### CORS Configuration
- Frontend Origin: `http://localhost:5173`
- Allowed Methods: GET, POST, PUT, DELETE, PATCH
- Allowed Headers: All
- Credentials: Allowed

## API Endpoints

### Public Endpoints (No Authentication Required)
- `POST /api/login` - User/Admin authentication
- `POST /api/users/create` - User registration

### Protected Endpoints (User & Admin Access)
- `GET /api/packages/**` - Package related operations
  - View all packages
  - View package details
  - Search packages

### Admin-Only Endpoints (Requires ADMIN Role)
- `POST /api/admin/**` - Admin operations
  - Manage packages
  - Manage users
  - View bookings

### Authentication Flow
1. User/Admin sends credentials to `/api/login`
2. Server validates and returns JWT token
3. Client includes JWT token in Authorization header for subsequent requests
4. Server validates token through `JwtRequestFilter`

## Project Structure

```
travel-booking/
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── AddToCart/
│   │   │   ├── AdminCreatePackage/
│   │   │   ├── AdminEditDeletePackage/
│   │   │   ├── AdminLogin/
│   │   │   ├── UserLogin/
│   │   │   └── UserViewPackages/
│   │   ├── App.js
│   │   └── index.js
│   └── package.json
│
└── backend/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/travelbooking/
    │   │   │       ├── config/
    │   │   │       │   ├── SecurityConfig.java
    │   │   │       │   └── JwtRequestFilter.java
    │   │   │       ├── controllers/
    │   │   │       ├── models/
    │   │   │       ├── repositories/
    │   │   │       └── services/
    │   │   └── resources/
    │   │       └── application.properties
    └── pom.xml
```

## Available Pages

1. **User Side**
   - User Login `/login`
   - Package Listing `/packages`
   - Package Details `/package/:id`
   - Shopping Cart `/cart`
   - Booking Confirmation `/booking`

2. **Admin Side**
   - Admin Login `/admin/login`
   - Create Package `/admin/create-package`
   - Edit/Delete Packages `/admin/manage-packages`
   - Booking Management `/admin/bookings`

## Contributing

Please read `CONTRIBUTING.md` for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the `LICENSE.md` file for details

## Screenshots

### User Interface
![User Login Page](https://github.com/HJHitesh/Skeleton_Springboot_JWT/blob/master/src/main/resources/static/images/Login_user.png)
![Package Listing](<Add screenshot here>)
![Shopping Cart](<Add screenshot here>)
![Booking Page](<Add screenshot here>)

### Admin Interface
![Admin Login](<Add screenshot here>)
![Create Package](<Add screenshot here>)
![Manage Packages](<Add screenshot here>)
![Booking Management](<Add screenshot here>)
