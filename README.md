Web Calendar

<p>A REST service on the Spring Boot framework with a database using Spring Data JPA and resources using Spring MVC.</p>

Examples

> Example 1: GET request for the /event?start_time=2020-10-10&end_time=2020-10-20 endpoint

Response Body:

Response: 200 OK

[
   {
      "id":1,
      "event":"Video conference",
      "date":"2020-10-15"
   },
   {
      "id":2,
      "event":"Today's first event",
      "date":"2020-10-20"
   }
]

> Example 2: GET request for the /event/1 endpoint

Response: 200 OK

Response Body:

{
    "id":1,
    "event":"Video conference",
    "date":"2020-10-15"
}

> Example 3: GET request for the /event/10 endpoint

Response: 404 Not Found

Response Body:

{
    "message": "The event doesn't exist!"
}

> Example 4: DELETE request for the /event/1 endpoint

Response: 200 OK

Response Body:

{
    "id":1,
    "event":"Video conference",
    "date":"2020-10-15"
}

> Example 5: DELETE request for the /event/10 endpoint

Response: 404 Not Found

Response Body:

{
    "message": "The event doesn't exist!"
}

