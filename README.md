# Test system of programming tasks backend

### Add a contest to the system (only teacher can make it):

```curl
curl --location 'http://localhost:8080/api/contest/add' \
--header 'Content-Type: application/json' \
--data '{
    "title": "Test contest",
    "ejudge_id": 1,
    "start_time": "2024-05-02T18:00:00",
    "finish_time": "2024-05-02T18:00:00",
    "tasks": [
        {
            "title": "A. First task",
            "description": "Task description. Solve it",
            "memory_limit": 32,
            "time_limit": 2,
            "attempts_amount": 100,
            "classification_title": "type_1"
        }
    ]
}'
```

Response is some kind of string. If something is wrong, error is thrown

## Authorization

POST http://localhost:8080/api/auth/login

#### Request body

```json
{
  "email": "ebstartsev@edu.hse.ru",
  "password": "qwerty"
}
```

#### Response

```json
{
  "role": "student",
  "token": "token"
}
```

`"role"` can also be `"teacher"`

## Solutions

### Add solution

POST http://localhost:8080/api/solutions/add

#### Request body

```json
{
  "code": "some code",
  "language": "python3",
  "status": "ok",
  "used_time": 0.2,
  "used_memory": 2.8,
  "error_test": "N/A",
  "contest_name": "Contest 1",
  "task_name": "Task A"
}
```

Response is some kind of string

### Get solutions of user

GET http://localhost:8080/api/solutions/get-all-user

#### Response

```json
[
  {
    "id": 24,
    "code": "some code",
    "language": "python3",
    "status": "ok",
    "used_time": 0.2,
    "used_memory": 2.8,
    "error_test": "N/A",
    "contest_name": "Contest 1",
    "task_name": "Task A"
  },
  {
    "id": 25,
    "code": "some code",
    "language": "python3",
    "status": "ok",
    "used_time": 0.2,
    "used_memory": 2.8,
    "error_test": "N/A",
    "contest_name": "Contest 1",
    "task_name": "Task A"
  }
]
```

## Profile

### Get user info (for profile)

GET http://localhost:8080/api/profile/get-student-info

#### Response:

```json
{
  "id": 2,
  "first_name": "Евгений",
  "last_name": "Старцев",
  "middle_name": "Борисович",
  "email": "ebstartsev@edu.hse.ru",
  "department": "Информатика, инженерия и математика",
  "group": "10И3"
}
```