
A secure File & Folder Management System built using Spring Boot, JWT Authentication, and Role
Based Access Control (RBAC).
This project supports file upload, folder hierarchy, sharing, search, rename, soft delete, archive, and
restore functionalities similar to Google Drive.


🚀 Tech Stack:

•  Backend: Java, Spring Boot
•  Security: Spring Security, JWT
•  Database: PostgreSQL
•  ORM: Spring Data JPA, Hibernate
•  Mapping: ModelMapper
•  Build Tool: Maven
•  Testing: Postman


🔐 Authentication & Authorization:

Roles

•ADMIN → Full access (files, folders, share, delete)
•USER → Limited access (view/search folders & files)


Authentication:

•  JWT-based authentication
•  Token required for protected APIs


Public APIs:

•  /apiauth/**
•  /public/**


📂 Folder Module:

Entity

•  Folder hierarchy (Parent → Child)
•  Soft delete supported
•  Status-based management


APIs:

Method   Endpoint                     Role    Description

POST     /folders                     ADMIN   Create folder
GET      /folders/GetFolderByid/{id}  USER    Get folders by parent
PUT      /folders/update/{id}         ADMIN   Update folder
DELETE   /folders/{id}                ADMIN   Delete folder


Features:

•  Nested folder structure
•  Soft delete
•  Restore support


📄 File Module:

   Entity

   •  File metadata
   •  Linked with folder & owner
   •  Soft delete support


APIs:

Method     Endpoint                      Role     Description

POST       /files/upload                 ADMIN    Upload file
DELETE     /files/deleteFile/{id}        ADMIN    Delete file
GET        /files/getFileById/{id}       USER     Get file by ID
GET        /files/getAllFiles            ADMIN    Get all files
PUT        /files/rename/{fileShareId}   ADMIN    Rename file
POST       /files/starFile/{id}/star     ADMIN    Star file
GET        /files/search?keyword=        USER     Search files


🔍 Search Functionality:

•  Search files by name (case-insensitive)
•  Only non-deleted files returned
•  Accessible by USER role


� File Sharing Module:

Features

•  Share file with USER
•  Roles:
•  VIEWER
•  EDITOR


Validations:

•  Editor can rename****
•  Viewer can only vie


🗃 Archive Module:

Purpose

•  Store deleted files/folders
•  Maintain delete history

Behavior:

•  Data is archived only after successful delete
•  Archive table stores:
•  Name
•  Storage Path


🔁 Restore Functionality:

•  Restore soft-deleted files
•  Restore soft-deleted folders


🛡 Security Configuration:

•  Method-level security using
•  JWT filter applied before authentication
•  Role-based endpoint access


� API Testing (Postman):

Example: Login

POST http://localhost:9095/apiauth/login

{
"username": "Admin01",
"password": "1234"
}

Authorization Header:

Authorization: Bearer <JWT_TOKEN>


⚙ Application Properties:

   server.port=9095
   spring.datasource.url=jdbc:postgresql://localhost:5432/filedb
   spring.datasource.username=xyz
   spring.datasource.password=xyz
   spring.jpa.hibernate.ddl-auto=update

Project Architecture:

This project follows a Layered + Modular Spring Boot Architecture ensuring separation of concerns,
scalability, and maintainability. No functionality is skipped.

1. Presentation Layer (Controller)

   Package: com.example.fileManagment.demo.fileManagment.Controller
2. 
   Responsibilities:
   - Handle HTTP requests (REST APIs) 
   - Validate request parameters 
   - Return standardized
   
   API responses ( ApiSuccessResponse )
3. 
   Controllers: 
    - AuthController – Login & JWT generation 
    - FolderController – Folder CRUD & hierarchy 
    - FileController – File upload, delete, search, rename 
    - PublicLinkController – Public file access via token 
    - SharesController – File sharing & permissions
   

2. Service Layer (Business Logic)

   Packages: 
    - serviceI (Interfaces) 
    - serviceimpl (Implementations)
   
   Responsibilities: 
    - Business rules & validations 
    - Transaction management 
    - Role-based logic (viewer/editor/ admin) 
    - Archive handling after delete
   
   Services: 
    - AuthService 
    - FolderService 
    - FileService 
    - SharesService 
    - PublicLinkService
   


3. Repository Layer (Data Access)

   Package: repository

   Responsibilities: 
    - Database interaction using Spring Data JPA
    - Custom query methods
   
   Repositories: 
    - UserRepository 
    - FolderRepository 
    - FileRepository 
    - ShareRepository 
    - ArchiveRepository
    - PublicLinkRepository


4. Entity Layer (Database Models)

   Package: entity

   Entities: 
    - User – User details & role 
    - Folder – Folder hierarchy 
    - FileEntity – File metadata -
    - Shares – File sharing info 
    - Archive – Deleted data history 
    - PublicLink – Token-based public access


5. DTO Layer (Request / Response Models)

   Packages: 
    - dto.request 
    - dto.response
   
   Purpose: 
    - Prevent direct entity exposure 
    - Control API response format
   
   Key DTOs: 
    - foldercreate 
    - FileResponse 
    - FileUploadResponse 
    - ApiSuccessResponse 
    - PublicLinkRequest / Response


6. Security Layer (JWT & RBAC)

   Package: config

   Components: 
     - SecurityConfig 
     - JwtAuthenticationFilter
     - JwtAuthenticationEntryPoint 
     - JwtAccessDeniedHandler


7. Exception & Utility Layer

   Packages: 
     - exception 
     - config
   
   Includes: 
     - Global exception handler 
     - Custom exceptions  
     - ModelMapper configuration


8. Database Architecture

   Tables: 
     - users 
     - folders 
     - files 
     - shares 
     - archives 
     - public_links

📌 Key Highlights:

✔ Secure JWT authentication
✔ Role-based access (ADMIN / USER)
✔ Soft delete with archive backup
✔ Folder hierarchy support
✔ File sharing with permissions
✔ Search & rename functionality
✔ Clean API responses using(ApiSuccessResponse)


📁 Project Structure (Reference):

fileManagment/
├── pom.xml
├── README.md
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/example/fileManagment/demo/fileManagment/
│ │ │ ├── FileManagmentApplication.java
│ │ │ │
│ │ │ ├── config/
│ │ │ │ ├── SecurityConfig.java
│ │ │ │ ├── JwtAuthenticationFilter.java
│ │ │ │ ├── JwtAuthenticationEntryPoint.java
│ │ │ │ ├── JwtAccessDeniedHandler.java
│ │ │ │ └── JwtUtil.java
│ │ │ │
│ │ │ ├── Controller/
│ │ │ │ ├── FileController.java
│ │ │ │ ├── FolderController.java
│ │ │ │ ├── ShareController.java
│ │ │ │ ├── PublicLinkController.java
│ │ │ │ └── AuthController.java
│ │ │ │
│ │ │ ├── serviceI/
│ │ │ │ ├── FileService.java
│ │ │ │ ├── FolderService.java
│ │ │ │ ├── SharesService.java
│ │ │ │ └── PublicLinkService.java
│ │ │ │
│ │ │ ├── serviceimpl/
│ │ │ │ ├── FileServiceImpl.java
│ │ │ │ ├── FolderServiceImpl.java
│ │ │ │ ├── ShareServiceImpl.java
│ │ │ │ └── PublicLinkServiceImpl.java
│ │ │ │
│ │ │ ├── repository/
│ │ │ │ ├── FileRepository.java
│ │ │ │ ├── FolderRepository.java
│ │ │ │ ├── ShareRepository.java
│ │ │ │ ├── UserRepository.java
│ │ │ │ ├── ArchiveRepository.java
│ │ │ │ └── PublicLinkRepository.java
│ │ │ │
7
│ │ │ ├── entity/
│ │ │ │ ├── FileEntity.java
│ │ │ │ ├── Folder.java
│ │ │ │ ├── Shares.java
│ │ │ │ ├── User.java
│ │ │ │ ├── Archive.java
│ │ │ │ └── PublicLink.java
│ │ │ │
│ │ │ ├── dto/
│ │ │ │ ├── request/
│ │ │ │ │ ├── foldercreate.java
│ │ │ │ │ ├── sharesRequest.java
│ │ │ │ │ ├── PublicLinkRequest.java
│ │ │ │ │ └── LoginRequest.java
│ │ │ │ │
│ │ │ │ ├── response/
│ │ │ │ │ ├── ApiSuccessResponse.java
│ │ │ │ │ ├── FileResponse.java
│ │ │ │ │ ├── FileUploadResponse.java
│ │ │ │ │ ├── FolderResponse.java
│ │ │ │ │ ├── SharesResponse.java
│ │ │ │ │ ├── PublicLinkResponse.java
│ │ │ │ │ └── FileSharedRenameResponseDto.java
│ │ │ │
│ │ │ ├── exception/
│ │ │ │ ├── ResourceNotFoundException.java
│ │ │ │ ├── UnauthorizedException.java
│ │ │ │ └── GlobalExceptionHandler.java
│ │ │ │
│ │ │ ├── enam/
│ │ │ │ ├── ShareRole.java
│ │ │ │ └── FolderStatus.java
│ │ │ │
│ │ │ └── util/
│ │ │ └── JwtConstants.java
│ │ │
│ │ └── resources/
│ │ ├── application.properties
│ │ └── static/
│ │
│ └── test/
│ └── java/
│ └── com/example/fileManagment/
│ └── FileManagmentApplicationTests.java



🧪 How to Run the Project:

   Clone the repository
   git clone : https://github.com/chetanamahant/fileManagment 

🚀 Future Enhancements:

   The following features can be added in future versions of the File Management System to make it more 
   scalable, secure, and production-ready:

🔐 Security Enhancements:

   - Refresh Token implementation for better JWT lifecycle management
   - Role-based permissions at folder & file level (custom ACL)
   - Multi-factor authentication (OTP / Email verification)
   - Rate limiting to prevent brute-force attacks

☁ Storage & Performance:

   - Integration with cloud storage (AWS S3 / Google Cloud Storage)
   - File versioning (track previous versions of files)
   - Large file upload using chunk-based upload
   - Caching using Redis for faster file & folder access

🧠 Advanced Features:

   - Trash bin with auto-delete after configurable days
   - Restore files/folders from archive
   - Folder sharing with expiration time
   - Public link analytics (download count, last accessed)

📊 Monitoring & Logging:

   - Centralized logging using ELK stack
   - API performance monitoring (Actuator + Prometheus)
   - Audit logs for file access, rename, delete operations


💻 Author:

Chetana Mahant
Java | Spring Boot | PostgreSQL