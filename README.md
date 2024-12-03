# ResearchManager

## Overview

ResearchManager is a Jakarta EE-based Java application designed to manage research projects efficiently. The application
leverages various Jakarta EE specifications to provide a robust, modular, and scalable architecture.

## Features

The project utilizes the following Jakarta EE APIs and technologies:

- **Jakarta Context and Dependency Injection (CDI)**: For dependency injection and managing the lifecycle of beans.
- **Jakarta Server Faces (JSF)**: For building the web interface.
- **Jakarta RESTful Web Services (JAX-RS)**: For creating RESTful endpoints.
- **Jakarta Persistence (JPA)**: With H2 database integration for data storage.
- **Jakarta Enterprise Beans (EJB)**: For business logic and transactions.
- **Jakarta Security API**: For application security.
- **Jakarta Bean Validation**: For validating user inputs.

## Prerequisites

To run this project, you will need:

- JDK 17 or later
- Maven

## How to Run

1. Clone this repository to your local machine.
2. Navigate to the project directory in your terminal.
3. Run the application using the following Maven command:
   ```bash
   mvn -P liberty liberty:dev
    ```

This command will:

- Start the Open Liberty server in development mode.
- Deploy the application to the server.

## Development Notes

- H2 Database: The application uses an in-memory H2 database for persistence.
- Lombok: Utilized for boilerplate code reduction. Ensure your IDE supports Lombok annotations.

## Dependencies

The application depends on the following libraries:

- jakarta.jakartaee-api (version 10.0.0)
- lombok (version 1.18.26)
- mdb-ui-kit (version 6.4.0)

For more details, check the pom.xml file.

## License

This project is licensed under the [MIT](LICENSE) License.