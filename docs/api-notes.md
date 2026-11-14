# Atelier 5 — REST API, Jackson et SpringDoc

## A. Endpoints

All routes below are prefixed by `/api` through `server.servlet.context-path`.

| Controller | Verb and route | Service call | Success | Errors |
|---|---|---|---|---|
| Agence | GET `/agences` | `findAll()` | 200 | — |
| Agence | GET `/agences/{id}` | `findById(id)` | 200 | 404 |
| Agence | POST `/agences` | `create(entity)` | 201 | 400 |
| Agence | PUT `/agences/{id}` | `update(id, entity)` | 200 | 400, 404 |
| Agence | DELETE `/agences/{id}` | `deleteById(id)` | 204 | 404 |
| Employe | GET `/employes` | `findAll()` | 200 | — |
| Employe | GET `/employes/{id}` | `findById(id)` | 200 | 404 |
| Employe | POST `/employes` | `create(entity)` | 201 | 400 |
| Employe | PUT `/employes/{id}` | `update(id, entity)` | 200 | 400, 404 |
| Employe | DELETE `/employes/{id}` | `deleteById(id)` | 204 | 404 |
| Vehicule | GET `/vehicules` | `findAll()` | 200 | — |
| Vehicule | GET `/vehicules/{id}` | `findById(id)` | 200 | 404 |
| Vehicule | POST `/vehicules` | `create(entity)` | 201 | 400 |
| Vehicule | PUT `/vehicules/{id}` | `update(id, entity)` | 200 | 400, 404 |
| Vehicule | DELETE `/vehicules/{id}` | `deleteById(id)` | 204 | 404 |
| Equipement | GET `/equipements` | `findAll()` | 200 | — |
| Equipement | GET `/equipements/{id}` | `findById(id)` | 200 | 404 |
| Equipement | POST `/equipements` | `create(entity)` | 201 | 400 |
| Equipement | PUT `/equipements/{id}` | `update(id, entity)` | 200 | 400, 404 |
| Equipement | DELETE `/equipements/{id}` | `deleteById(id)` | 204 | 404 |
| Client | GET `/clients` | `findAll()` | 200 | — |
| Client | GET `/clients/{id}` | `findById(id)` | 200 | 404 |
| Client | POST `/clients` | `create(entity)` | 201 | 400 |
| Client | PUT `/clients/{id}` | `update(id, entity)` | 200 | 400, 404 |
| Client | DELETE `/clients/{id}` | `deleteById(id)` | 204 | 404 |
| Reservation | GET `/reservations` | `findAll()` | 200 | — |
| Reservation | GET `/reservations/{id}` | `findById(id)` | 200 | 404 |
| Reservation | POST `/reservations` | `create(entity)` | 201 | 400 |
| Reservation | PUT `/reservations/{id}` | `update(id, entity)` | 200 | 400, 404 |
| Reservation | DELETE `/reservations/{id}` | `deleteById(id)` | 204 | 404 |
| Contrat | GET `/contrats` | `findAll()` | 200 | — |
| Contrat | GET `/contrats/{id}` | `findById(id)` | 200 | 404 |
| Contrat | POST `/contrats` | `create(entity)` | 201 | 400 |
| Contrat | PUT `/contrats/{id}` | `update(id, entity)` | 200 | 400, 404 |
| Contrat | DELETE `/contrats/{id}` | `deleteById(id)` | 204 | 404 |
| Paiement | GET `/paiements` | `findAll()` | 200 | — |
| Paiement | GET `/paiements/{id}` | `findById(id)` | 200 | 404 |
| Maintenance | GET `/maintenances` | `findAll()` | 200 | — |
| Maintenance | GET `/maintenances/{id}` | `findById(id)` | 200 | 404 |
| Maintenance | POST `/maintenances` | `create(entity)` | 201 | 400 |
| Maintenance | PUT `/maintenances/{id}` | `update(id, entity)` | 200 | 400, 404 |
| Maintenance | DELETE `/maintenances/{id}` | `deleteById(id)` | 204 | 404 |

`POST /paiements`, `PUT /paiements/{id}` and `DELETE /paiements/{id}` are intentionally absent; Spring MVC returns 405 because Paiement is managed through Contrat.

## B. Jackson association handling

| Association | Handling | Reason |
|---|---|---|
| Contrat.paiements / Paiement.contrat | `@JsonManagedReference` / `@JsonBackReference` with the same reference name | Keeps nested payment creation on Contrat while preventing recursive serialization |
| Agence.vehicules and Agence.employes | `@JsonIgnore` on inverse collections | Vehicles and employees can still accept an embedded Agence identifier; the reverse lists are not needed in those responses |
| Client.reservations | `@JsonIgnore` on inverse collection | Reservation.client remains serializable and writable, avoiding the cycle back through the client list |
| Equipement.vehicules | `@JsonIgnore` on inverse collection | Vehicle.equipements remains writable without a recursive back edge |
| Vehicule.maintenances | `@JsonIgnore` on inverse collection | Maintenance.vehicule remains available without cycling back through the vehicle list |
| Reservation.contrat | `@JsonIgnore` on inverse one-to-one | Contrat.reservation can still be represented without serializing back to the same contract |

`@JsonBackReference` suppresses a designated back edge during serialization and pairs with the managed side for composition. `@JsonIgnore` excludes the annotated property from JSON in both directions. This is why it is applied to inverse lists, not to `Reservation.client`, which must be available when creating a reservation.

## C. Postman and Swagger UI

Import `docs/postman/AutoLoc.postman_collection.json` and `docs/postman/local.postman_environment.json` into Postman and select the `local` environment. `baseUrl` includes `/api`. The collection contains CRUD request templates, missing-resource examples, and a POST payment request demonstrating the expected 405. Replace example payloads and linked entity IDs with records in the local database; unique values such as email, permit number, and registration plate must not already exist.

Postman sends and replays concrete requests and shows response status/body. SpringDoc generates the OpenAPI description from the controller code; Swagger UI lets a developer inspect and try the same endpoints. With the application and MySQL running, the API description is at `/api/v3/api-docs` and the Swagger UI at `/api/swagger-ui.html`.

## D. Static analysis

SonarQube for IDE was not available in the execution environment, so no IDE scan findings are claimed. The code uses constructor injection with final service fields, avoids generic exceptions for missing resources, and keeps controllers dependent on service interfaces. Run the IDE analysis to record tool-specific rule IDs and findings.

## E. Questions

1. `DispatcherServlet` receives the HTTP request; Spring MVC matches method and path mappings and invokes the matching controller handler.
2. The controller depends on `IContratService` to depend on the layer contract rather than a persistence component or a concrete implementation.
3. POST returns 201 because it creates a resource; DELETE returns 204 when deletion succeeds without a response body. A missing resource is a domain-level absence mapped by the advice to 404, not an unexpected server failure (500).
4. Payment writes go through Contrat so the aggregate's cascade and orphan removal stay consistent. A POST to `/api/paiements` is unsupported and returns 405.
5. Exposing entities can reveal persistence fields and couples the API to the database model; DTOs would separate those concerns. Contrat.paiements uses the managed/back pair because it is a composition that must be writable through the parent. Agence.vehicules is an inverse collection and is ignored to prevent recursive output while leaving Vehicule.agence usable.
