# Practice Library

## English

Practice Library is a small Spring Boot REST API for managing libraries and books. It was created as a practice project for building a simple backend application with a clear layered structure, DTOs, validation, JPA entities, repositories, and a many-to-many relationship between books and libraries.

The application allows users to create libraries, add books, search books by title, fetch library details, and connect existing books to existing libraries.

### Main Features

- Create libraries with a name and capacity
- Create one book or multiple books in a batch request
- Search books by title
- Get library details by name
- Add an existing book to an existing library
- Store data in MySQL with Spring Data JPA
- Validate incoming request bodies
- Return DTO-based responses instead of exposing entities directly

### Tech Stack

- Java 21
- Spring Boot 4
- Spring Web
- Spring Data JPA
- MySQL
- Lombok
- ModelMapper
- Maven

### Project Structure

```text
src/main/java/hu/progmasters/firstspringdemo
+-- configuration   # ModelMapper configuration
+-- controll        # REST controllers
+-- domain          # JPA entities and enums
+-- dto             # Incoming and outgoing DTOs
+-- repository      # Spring Data JPA repositories
+-- service         # Business logic
```

### Data Model

The project contains two main entities:

- `Library`: stores the library name, capacity, and its books.
- `Book`: stores the title, author, type, and connected libraries.

Books and libraries have a many-to-many relationship through the `library_book` join table.

Available book types:

```text
FICTION, NON_FICTION, SCIENCE, CHILDREN
```

### API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/libraries` | Create a library |
| `GET` | `/api/libraries/{name}` | Get a library by name |
| `POST` | `/api/books` | Create a book |
| `POST` | `/api/books/batch` | Create multiple books |
| `GET` | `/api/books/{title}` | Get one book by title |
| `GET` | `/api/books?title={title}` | Search books by title |
| `POST` | `/api/books/add-to-library` | Add a book to a library |

Example request:

```http
POST http://localhost:8080/api/libraries
Content-Type: application/json

{
  "name": "Libri",
  "capacity": 10000
}
```

More examples are available in [`intro.http`](intro.http).

### Running the Project

Requirements:

- Java 21
- Maven or the included Maven Wrapper
- Local MySQL database

The database connection is configured in `src/main/resources/application.yaml`.

On Windows:

```bash
./mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

The API runs on:

```text
http://localhost:8080
```

Run tests:

```bash
./mvnw test
```

### What I Practiced

- Building REST APIs with Spring Boot
- Using controller, service, repository, DTO, and domain layers
- Working with JPA entity relationships
- Adding request validation
- Mapping entities to response DTOs
- Connecting a Spring Boot application to MySQL

---

## Magyar

A Practice Library egy kisebb Spring Boot REST API könyvtárak és könyvek kezelésére. Gyakorlóprojektként készült, főleg azért, hogy átlátható backend struktúrán keresztül gyakorolja a REST kontrollereket, DTO-kat, validációt, JPA entitásokat, repository-kat és a könyvek-könyvtárak közötti több-a-többhöz kapcsolatot.

Az alkalmazással lehet könyvtárakat létrehozni, könyveket felvenni, cím alapján keresni, könyvtár adatokat lekérni, illetve meglévő könyveket meglévő könyvtárakhoz kapcsolni.

### Fő funkciók

- Könyvtár létrehozása névvel és kapacitással
- Egy könyv vagy több könyv létrehozása egyszerre
- Könyvek keresése cím alapján
- Könyvtár lekérése név alapján
- Meglévő könyv hozzáadása meglévő könyvtárhoz
- Adattárolás MySQL adatbázisban Spring Data JPA-val
- Beérkező kérések validálása
- DTO-k használata a válaszokhoz

### Technológiák

- Java 21
- Spring Boot 4
- Spring Web
- Spring Data JPA
- MySQL
- Lombok
- ModelMapper
- Maven

### Projekt felépítése

```text
src/main/java/hu/progmasters/firstspringdemo
+-- configuration   # ModelMapper konfiguráció
+-- controll        # REST kontrollerek
+-- domain          # JPA entitások és enumok
+-- dto             # Bejövő és kimenő DTO-k
+-- repository      # Spring Data JPA repository-k
+-- service         # Üzleti logika
```

### Adatmodell

A projekt két fő entitásra épül:

- `Library`: a könyvtár nevét, kapacitását és könyveit tárolja.
- `Book`: a könyv címét, szerzőjét, típusát és kapcsolódó könyvtárait tárolja.

A könyvek és könyvtárak között több-a-többhöz kapcsolat van, amelyet a `library_book` kapcsolótábla kezel.

Elérhető könyvtípusok:

```text
FICTION, NON_FICTION, SCIENCE, CHILDREN
```

### API végpontok

| Metódus | Végpont | Leírás |
| --- | --- | --- |
| `POST` | `/api/libraries` | Könyvtár létrehozása |
| `GET` | `/api/libraries/{name}` | Könyvtár lekérése név alapján |
| `POST` | `/api/books` | Könyv létrehozása |
| `POST` | `/api/books/batch` | Több könyv létrehozása |
| `GET` | `/api/books/{title}` | Egy könyv lekérése cím alapján |
| `GET` | `/api/books?title={title}` | Könyvek keresése cím alapján |
| `POST` | `/api/books/add-to-library` | Könyv hozzáadása könyvtárhoz |

Példa kérés:

```http
POST http://localhost:8080/api/books/add-to-library
Content-Type: application/json

{
  "title": "Clean Code",
  "libraryName": "Libri"
}
```

További példák az [`intro.http`](intro.http) fájlban találhatók.

### Futtatás

Szükséges:

- Java 21
- Maven vagy a projektben található Maven Wrapper
- Helyi MySQL adatbázis

Az adatbázis kapcsolat a `src/main/resources/application.yaml` fájlban állítható be.

Windows:

```bash
./mvnw.cmd spring-boot:run
```

macOS vagy Linux:

```bash
./mvnw spring-boot:run
```

Az API alapértelmezett címe:

```text
http://localhost:8080
```

Tesztek futtatása:

```bash
./mvnw test
```

### Mit gyakoroltam vele?

- REST API készítése Spring Boottal
- Controller, service, repository, DTO és domain rétegek használata
- JPA kapcsolatok kialakítása
- Request validáció
- Entitások DTO-vá alakítása
- Spring Boot alkalmazás összekötése MySQL adatbázissal
