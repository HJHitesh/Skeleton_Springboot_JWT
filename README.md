# Travel Booking Website

A full-stack travel booking platform built using React, Spring Boot, and MySQL. This application allows users to browse travel packages, make bookings, and includes an admin panel for package management.

## Features

### User Features
- User Registration and Login
- Browse Available Travel Packages
- View Package Details
- Add Packages to Cart
- Book Travel Packages
- View Booking History

### Admin Features
- Secure Admin Login
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
- Spring Security
- Spring Data JPA
- MySQL Database

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
   The frontend application will start on `http://localhost:3000`

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

## API Endpoints

### User APIs
- `POST /api/auth/login` - User login
- `GET /api/packages` - Get all packages
- `GET /api/packages/{id}` - Get package details
- `POST /api/bookings` - Create booking

### Admin APIs
- `POST /api/admin/auth/login` - Admin login
- `POST /api/admin/packages` - Create package
- `PUT /api/admin/packages/{id}` - Update package
- `DELETE /api/admin/packages/{id}` - Delete package

## Contributing

Please read `CONTRIBUTING.md` for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the `LICENSE.md` file for details
