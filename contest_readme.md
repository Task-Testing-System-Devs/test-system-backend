## Флоу контеста

Добавляем контест:
1. Сначала отправляем запрос на создание контеста на обработчик

<здесь будет ссылка на запрос на создание контеста на обработчике>

2. Потом отправляем запрос на отправляем запрос на бэк со всей необходимой информацией, в ответ получаем
сообщение о том, что все создалось или нет.

http://backend-url/api/contest/add

Получаем контест и условия задачи - с бэка. Там же получаем айдишник контеста на еджадже и айдишник задачи на еджадже.

http://backend-url/api/contest/info?id=1

Флоу отправки решения в систему:

1. Отправляем решение задачи - на обработчик POST (http://handler-url/api/master/submit-run)
2. Получаем результат посылки с обработчика GET (http://handler-url/api/master/get-submit)
3. Добавляем информацию о посылке POST (http://backend-url/api/solutions/add)

## Запросы с телами

### Create contest (only teacher can make it):

POST http://localhost:8080/api/contest/add

#### Request body

```json
{
  "title": "Test contest",
  "start_time": "2024-05-02T18:00:00",
  "finish_time": "2024-05-02T18:00:00",
  "difficulty": 1,
  "tasks": [
    {
      "title": "A. First task",
      "description": "Task description. Solve it",
      "memory_limit": 32,
      "time_limit": 2,
      "attempts_amount": 100,
      "classification_title": "type_1"
    }
  ],
  "sample_tests": [
    {
      "input": "1 2",
      "output": "3"
    }
  ],
  "tests": [
    {
      "input": "1 2",
      "output": "3"
    }
  ]
}
```

Response is some kind of string

### Get contest

GET http://localhost:8080/api/contest/info?id=1

#### Response

```json
{
  "id": 1,
  "ejudge_id": 1,
  "title": "Test contest",
  "start_time": "2024-05-02T18:00:00",
  "finish_time": "2024-05-02T18:00:00",
  "difficulty": 1,
  "tasks": [
    {
      "ejudge_id": 1,
      "title": "A. First task",
      "description": "Task description. Solve it",
      "memory_limit": 32,
      "time_limit": 2,
      "attempts_amount": 100,
      "classification_title": "type_1",
      "sample_tests": [
        {
          "input": "1 2",
          "output": "3"
        }
      ]
    }
  ]
}
```

### Get all contests

GET http://localhost:8080/api/contest/get-all

#### Response

```json
[
    {
        "id": 3,
        "ejudge_id": 1,
        "title": "Test contest",
        "start_time": "2024-05-02T18:00:00",
        "finish_time": "2024-05-02T18:00:00",
        "difficulty": 1,
        "tasks": null
    },
    {
        "id": 4,
        "ejudge_id": 1,
        "title": "Test contest",
        "start_time": "2024-05-02T18:00:00",
        "finish_time": "2024-05-02T18:00:00",
        "difficulty": 1,
        "tasks": null
    }
]
```

### Submit a task for checking

```
curl --location 'http://localhost:3000/api/master/submit-run' \
        --form 'contest_id="1"' \
        --form 'problem="1"' \
        --form 'lang_id="23"' \
        --form 'file=@"/Users/startsev/Desktop/test.py"'
```

### Response

```json
{
  "message": "Submission handled successfully",
  "data": {
    "ok": true,
    "server_time": 1710244287,
    "result": {
      "run_id": 15,
      "run_uuid": "e8b889da-844c-454e-9379-01b108b1e28c"
    }
  }
}
```

### Get info about the run

```
curl --location 'http://localhost:3000/api/master/get-submit?contest_id=1&run_id=2'
```

### Response

```json
{
  "message": "Submit info fetched successfully",
  "data": {
    "ok": true,
    "server_time": 1710244846,
    "result": {
      "run": {
        "run_id": 2,
        "run_uuid": "212bbfa8-9f74-4280-bcab-38eefda21835",
        "serial_id": 3,
        "status": 5,
        "status_str": "WA",
        "run_time": 1709994179,
        "nsec": 388618000,
        "run_time_us": 1709994179388618,
        "duration": 521292,
        "user_id": 1,
        "user_login": "ejudge",
        "user_name": "ejudge administrator",
        "prob_id": 2,
        "prob_name": "B",
        "lang_id": 23,
        "lang_name": "python3",
        "ip": "38.180.14.27",
        "sha1": "32c2f38995b6a3c761707453ad63814eeeb6cf87",
        "size": 39,
        "is_hidden": true,
        "passed_mode": 1,
        "raw_test": 0,
        "verdict_bits": 16,
        "last_change_us": 1709994179460414
      }
    }
  }
}
```

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