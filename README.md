# SmartCampusRestAPI
A RESTful Smart Campus API built using JAX-RS to manage rooms, sensors, and sensor readings with proper resource design, sub-resource nesting, filtering, and robust error handling.

## 🧱 Features

* Room management (create, retrieve, delete)
* Sensor management with validation
* Sensor readings using sub-resource pattern
* Filtering using query parameters
* Business rule enforcement (e.g., cannot delete room with sensors)
* Structured error handling:

  * 409 Conflict
  * 422 Unprocessable Entity
  * 403 Forbidden
  * 500 Internal Server Error
* Request & response logging using filters

---

## 🔗 API Endpoints

### Rooms

* `GET /api/v1/rooms`
* `POST /api/v1/rooms`
* `GET /api/v1/rooms/{id}`
* `DELETE /api/v1/rooms/{id}`

### Sensors

* `GET /api/v1/sensors`
* `POST /api/v1/sensors`
* `GET /api/v1/sensors?type=Temperature`

### Sensor Readings

* `GET /api/v1/sensors/{id}/readings`
* `POST /api/v1/sensors/{id}/readings`

---

## 🚀 How to Run

1. Install JDK 11
2. Start Payara Server
3. Deploy the project using NetBeans or Maven
4. Access the API at:
   `http://localhost:8080/SmartCampus/api/v1`

---

## 📄 Report

### Service Architecture & Setup

**1. Explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.**

• Resource classes are automatically instantiated in JAX-RS, so that every incoming HTTP request is served by a new instance. This model is thread safe as no two requests share an instance. This however has an impact on data management. Because a new object is created on each request, e.g. the variables are not shared between requests. To ensure continued existence of data, shared data structures like HashMap or ArrayList should be declared as static. The data in this project is stored in static collections of Rooms, Sensors and Sensor Readings, so that it will continue to exist even after multiple API calls.


**2. Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?**

• HATEOAS (Hypermedia as the Engine of Application State) is a principle of REST, in which responses to API calls contain links to adjacent resources. This enables dynamical navigation of the clients through the API without prior endpoint knowledge. The discovery endpoint has links, including /rooms and /sensors, in this API that allow clients to browse the available resources. This enhances flexibility, less reliance on documentation, and makes the API self-descriptive.

---

### Room Management

**3. When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.**

• Sending IDs alone makes the response smaller, enhancing network performance and efficiency. Nonetheless, it needs further API requests to obtain comprehensive information. Full object returns present all the information within a single response, which is more usable, but has a larger payload. Here, the entire objects are returned to facilitate ease of use and better readability whereas the IDs are internal and are used to ensure relationships among the entities.


**4. Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.**

• Yes, the DELETE operation is idempotent. This implies that a repeated DELETE request would give the same end state. In this implementation, after a room has been deleted, further DELETE requests to the same room will not affect the system state. The API also avoids the deletion of rooms with sensors and ensures the integrity of data and avoids orphaned resources.

---

### Sensor Operations & Linking

**5. We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?**

• The @Consumes(MediaType.APPLICATION_JSON) annotation only allows the API to receive only JSON-formatted requests. In case a client transfers data in a different format, say XML or plain text, the server will discontinue the request and would issue an HTTP 415 (Unsupported Media Type) error. This guarantees data consistency in data processing and eliminates parsing errors.


**6. You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?**

• Filtering is more appropriate with query parameters since they are optional and flexible. As an illustration, /sensors?type=CO2 can be filtered without changing the resource structure. Path parameters are usually applied to the identification of particular resources (e.g. /sensors/{id}), and query parameters are more suitable to search and filtering. This separation enhances the API design understanding and utilization.

---

### Sub-Resources

**7. Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?**

• Sub-resource Locator pattern - This pattern allows separation of responsibilities that manage the nested resources, which enhances their modularity and maintainability. SensorReadingResource is used in this project to handle the readings related to a given sensor. This model maintains the code clean, puts responsibilities apart, and simplifies the API to scale and extend over what would be possible with all logic in a single resource class.

---

### Error Handling, Exception Mapping & Logging

**8. Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?**

• Unprocessable Entity (HTTP 422) is a response given when the request is well-formed, but has invalid data. As a case in point, designing a sensor with an imaginary room ID. The HTTP 404 (Not Found) means that there is no document of the requested resource itself. As the request structure is correct, but the data is wrong, 422 is a better response.


**9. From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?**

• Exposing stack traces can give sensitive internal details like class names, file structure and system architecture. Attackers can use this information to determine vulnerabilities. To avoid this the API employs a global exception handler which responds with a generic 500 Internal Server Error, but does not disclose internal details.


**10. Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?**

• JAX-RS filters can be used on logging to enable cross-cutting concerns to be handled centrally. This can save on duplication of logging code in all resource methods and provide uniform logging among all endpoints. All incoming requests and outgoing responses are intercepted by filters, thus simplifying the maintenance of the system and enhancing observability.

