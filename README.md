# Ticket Booking System

## Requirements

1. JDK 11
2. MySQL server

## How to Run

This app requires 4 environment variables

1. **MYSQL_JDBC_URL** = `jdbc:mysql://&lt;host&gt;:&lt;port&gt;/<db_name>?createDatabaseIfNotExist=true`
2. **MYSQL_USERNAME** = `your-mysql-username-here`
3. **MYSQL_PASSWORD** = `your-mysql-password-here`
4. **JWT_SECRET_KEY** = `a-random-string`

### To Run

### Linux/ MAC

```sh
./gradlew bootRun
```

### Windows

```sh
gradlew bootRun
```

## Implementations

### Backend

1. GET /auth/exists: 404 if no users exists, 200 if user exists
2. POST /auth/signup: Sign new user up
3. POST /auth/signin: Sign user in

SignUp/ SignIn format:

```json
{
	"username": "username",
	"password": "password"
}
```

SignIn returns a Bearer Token.

4.  GET /api/movies: Returns list of movies
5.  POST /api/movies: Add a new movie
6.  GET /api/movies/:id : Get movie with id
7.  POST /api/movies/:id/time : Add timing for movie with id
8.  GET /api/times: Returns list of all timings
9.  POST /api/times/:id/buy: Buy tickets for time-slot with id

Format for Adding Movie:

```json
{
	"name" : "name",
	"description" : "description",
	"rating": "rating",
	"director" : "director"
	"duration": 1242353
}
```

Format for Adding Time:

```json
{
	"startTime": 125636,
	"endTime": 12325,
	"ticketPrice": 21,
	"numberOfTickets": 300
}
```

Format for Buying Ticket:

```json
{
	"noOfTickets": 3
}
```

## TODO:

1. Create Frontend
2. Add validation for overlapping timings
3. Don't add the same movie twice
4. Improve error callbacks
