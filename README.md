# 🏘️ DietiEstates

Piattaforma web per la gestione di immobili sviluppata con **Spring Boot** (backend) e **Angular** (frontend). Il sistema permette agli agenti immobiliari di gestire proprietà, clienti e collaboratori attraverso un'interfaccia moderna e responsive.

## 🛠️ Stack Tecnologico

- **Backend**: Spring Boot 3.4.0 + Java 17 + PostgreSQL
- **Frontend**: Angular 19 + TypeScript + Leaflet Maps
- **DevOps**: Docker + Docker Compose

## � Avvio Rapido con Docker Compose

### 1. Clona il repository
```bash
git clone https://github.com/MirkoPrevenzano/INGSW-DietiEstates2025.git
cd INGSW-DietiEstates2025
```

### 2. Configura i file `.env`

**Backend** (`backend/.env`):
```properties
DATASOURCE_URL=jdbc:postgresql://database:5432/dieti_estate_db
DATASOURCE_USERNAME=postgres
DATASOURCE_PASSWORD=your_password
JWT_SECRET=your-jwt-secret-key
POSTGRES_DB=dieti_estate_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_password
```

**Frontend** (`.env` nella root):
```properties
GOOGLE_API_KEY=your-google-client-id
GEOAPIFY_TOKEN=your-geoapify-token
API_BASE_URL=http://localhost:8080
```

### 3. Avvia l'applicazione
```bash
docker-compose up -d
```

L'applicazione sarà disponibile su:
- **Frontend**: http://localhost:4200
- **Backend**: http://localhost:8080
- **Database**: localhost:5433

### 4. Stop dell'applicazione
```bash
docker-compose down
```

## 💻 Sviluppo Locale

### Backend (Spring Boot)
```bash
cd backend
./mvnw spring-boot:run
```
Server disponibile su: http://localhost:8080

### Frontend (Angular)
```bash
cd frontend
npm install
npm start
```
Applicazione disponibile su: http://localhost:4200

## 🧪 Test

**Backend**:
```bash
cd backend
./mvnw test
```

**Frontend**:
```bash
cd frontend
npm test
```

## 📁 Struttura del Progetto

```
INGSW-DietiEstates2025/
├── backend/              # Spring Boot API
│   ├── src/main/java/   # Codice sorgente
│   ├── src/test/        # Test
│   └── pom.xml          # Dipendenze Maven
├── frontend/            # Angular App
│   ├── src/app/         # Componenti e servizi
│   ├── package.json     # Dipendenze npm
│   └── Dockerfile       # Configurazione Docker
├── docker-compose.yml   # Orchestrazione container
└── README.md
```



**Made with ❤️ by DietiEstates Team**
