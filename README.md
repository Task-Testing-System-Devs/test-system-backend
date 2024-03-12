# Test system of programming tasks backend

Add a contest to the system (only teacher can make it):

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

Response is some kind of string

