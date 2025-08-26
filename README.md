# Resume Screener

## 🚀 Setup & Installation

### 1. Clone the repository
git clone https://github.com/<your-username>/resume-screener.git  
cd resume-screener

### 2. Backend (Spring Boot + MongoDB)
- Navigate to the backend folder:  
  cd backend

- Create your `.env` file:  
  cp .env.example .env  
  Fill in your real values (MongoDB URI, database name, JWT secret).

- Create your `application.properties`:  
  cp application-example.properties application.properties  
  (No need to edit unless you want to change server port or logging levels.)

- Run the backend:  
  mvn spring-boot:run

### 3. Frontend (Vite + React)
- Navigate to the frontend folder:  
  cd ../frontend

- Install dependencies:  
  npm install

- Start the development server:  
  npm run dev

### 4. Open the app
- Backend: http://localhost:8080  
- Frontend: http://localhost:5173 (Vite default)

### 5. Notes
- **Secrets:** Never commit your `.env` or `application.properties`.  
- **Example files:**  
  - backend/.env.example → template for secrets  
  - backend/application-example.properties → template for Spring config  
- **Production:** Set environment variables directly on your server/cloud instead of using `.env`.

---

## 🛠️ Tech Stack

### Frontend
- ⚡ Vite – Fast build tool & dev server  
- ⚛️ React – Component-based UI library  
- 🎨 Tailwind CSS (if you’re using it) – Utility-first styling  
- 🔄 Fetch API / Axios – For making API calls  

### Backend
- ☕ Java 17+ – Language  
- 🚀 Spring Boot – Backend framework  
- 🔐 JWT (JSON Web Tokens) – Authentication & authorization  
- 📦 Maven – Dependency management  

### Database
- 🍃 MongoDB – NoSQL database  
- ☁️ MongoDB Atlas – Cloud-hosted MongoDB cluster  

### Others
- 📝 .env for secrets management  
- 🛠️ Git & GitHub for version control  
- 🖥️ VS Code / IntelliJ IDEA for development

### Project Structure

resume-screener/
│── frontend/
│   │── public/
│   │── src/
│   │── package.json
│   │── vite.config.js
│   └── ...
│
│── backend/
│   │── src/
│   │   └── main/java/...     
│   │   └── main/resources/
│   │
│   │── pom.xml
│   │── .env
│   │── .env.example
│   │── application.properties
│   └── target/
│
│── .gitignore
│── README.md



## 📸 Screenshots

👋 **Welcome Page**  
![Welcome Page](./screenshots/welcomepage.png)

🏠 **Home Page**  
![Home Page](./screenshots/homepage.png)

ℹ️ **About Page**  
![About Page](./screenshots/aboutpage.png)

📞 **Contact Page**  
![Contact Page](./screenshots/contactpage.png)

🔐 **Login Page**  
![Login Page](./screenshots/loginpage.png)

📝 **Signup Page**  
![Signup Page](./screenshots/signuppage.png)

📂 **Resume Upload**  
![Resume Upload](./screenshots/resumeupload.png)
