# Smart Contact Manager

## Overview
**Smart Contact Manager** is a modern web application that allows you to store, manage, and interact with your contacts seamlessly in the cloud. With features like direct mailing, contact management, and secure authentication, this application provides a comprehensive solution for your contact management needs. Built with a robust tech stack, including Spring Boot, Thymeleaf, Tailwind CSS, Cloudinary, Spring Security, Hibernate, and Spring MVC, it ensures a responsive and secure user experience.

## Features
- **Contact Storage in the Cloud:** Safely store your contacts in the cloud, with easy access from anywhere.
- **Direct Mailing:** Send emails directly to your contacts with just a click.
- **Contact Management:** Easily add, update, or delete contacts. Update contact details like name, address, phone number, and photo.
- **Photo Upload and Management:** Upload and manage contact photos using Cloudinary, a powerful cloud-based image management service.
- **Secure Authentication:** Leverage Spring Security for secure user authentication and authorization.
- **Responsive Design:** Enjoy a sleek and responsive user interface designed with Tailwind CSS, providing both light and dark mode options.
- **Data Persistence:** Use Hibernate to ensure reliable data storage and retrieval from the database.
- **MVC Architecture:** Clean and maintainable code structure with Spring MVC.

## Tech Stack
- **Backend:**
  - Spring Boot
  - Spring MVC
  - Hibernate
  - Spring Security
  - Spring Cloud (for cloud integration)
  
- **Frontend:**
  - Thymeleaf (for server-side rendering)
  - Tailwind CSS (for styling)

- **Cloud Services:**
  - **Cloudinary:** For storing and managing images.
  
- **Database:**
  - MySQL (or any other relational database supported by Hibernate)

## Setup Instructions
### Prerequisites
- Java 17+
- Maven
- MySQL or any supported relational database
- Cloudinary account (for image management)
- SMTP server credentials (for mailing functionality)

### Installation
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/sunnysingh3972/scm-smart-contact-manager.git
   cd scm-smart-contact-manager
   ```

2. **Configure the Application:**
   - Open `src/main/resources/application.properties` and `application-dev.properties` to set your database and cloud service configurations.
   - Set up Cloudinary API credentials and database connection details.
   - Configure your email SMTP server details for the mailing feature.

3. **Build and Run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the Application:**
   - Open your web browser and go to `http://localhost:8080`.
   - Register a new user or log in with existing credentials.

### Usage
- **Dashboard:** View your contacts and perform CRUD (Create, Read, Update, Delete) operations.
- **Add Contact:** Fill in the contact details and upload a photo.
- **Manage Contact:** Update or delete existing contacts.
- **Send Email:** Directly send an email to any contact from within the app.

### Security
- **Authentication:** Uses Spring Security for login and session management.
- **Authorization:** Role-based access control to ensure users can only access their contacts.
  
## Contributing
We welcome contributions! Please fork the repository and create a pull request with your changes.

### Contribution Steps:
1. Fork the repository.
2. Create a new feature branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m "Add some feature"`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.


  
---
