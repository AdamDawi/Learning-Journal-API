# Learning Journal API

A RESTful API built with Spring Boot and Kotlin to help users track their learning sessions. It supports user authentication with JWT (access + refresh tokens), stores data in cloud MongoDB Atlas, and offers full CRUD functionality for learning sessions. See [API documentation](#-postman-api-documentation) for available endpoints.

## 🚀 Features

- ✅ User registration and login
- 🔐 JWT-based authentication (access + refresh tokens)
- 🧾 Create, read, update, delete learning sessions
- 🗃️ Remote MongoDB Atlas database
- 🛡️ Secure password and token hashing
- 📏 Input validation

## 🛠 Technologies

- Kotlin
- Spring Boot (Web, Security, Validation)
- MongoDB
- JWT (access + refresh tokens)

## 📦 API Endpoints

### 🔐 Authentication

| Method | Endpoint         | Description            |
|--------|------------------|------------------------|
| POST   | `/auth/register` | Register a new user    |
| POST   | `/auth/login`    | Log in to the system   |
| POST   | `/auth/refresh`  | Refresh JWT tokens     |

### 🧠 Learning Sessions (requires JWT Access Token in `Authorization` header)

| Method | Endpoint                      | Description                                 |
|--------|-------------------------------|---------------------------------------------|
| GET    | `/learning_sessions`          | Get all learning sessions for the user     |
| POST   | `/learning_sessions`          | Create or update a learning session        |
| DELETE | `/learning_sessions/{id}`     | Delete a learning session by ID            |

## 📚 Postman API documentation
You can find a collection of the API endpoints with example requests and responses in this [Postman collection](https://documenter.getpostman.com/view/36810282/2sB2ixiDNG).

## 💻 Running Locally

1. **Clone the repository:**
```bash
git clone https://github.com/AdamDawi/Learning-Journal-API.git
```
2. **Create a MongoDB Atlas account:**
- Go to https://www.mongodb.com/cloud/atlas and sign up.
- Create a free cluster.
- Add a user and whitelist your IP.
- Get your connection string, which will look like:
```
mongodb+srv://<username>:<password>@<cluster>.mongodb.net/learning-journal
```
3. **Set up environment variables in IntelliJ:**
- Open Run > Edit Configurations...
- Add the following environment variables in your run configuration:
```
spring.data.mongodb.uri=<YOUR_MONGODB_CONNECTION_STRING>
jwt.secret=<YOUR_JWT_SECRET_BASE64>
```
> Tip: You can generate a base64 secret using a tool like [base64encode.org]()

4. **Run the server:**
- In IntelliJ, run Application.kt
- Or from the terminal:
```bash
./gradlew bootRun
```
## Author

Adam Dawidziuk🧑‍💻
