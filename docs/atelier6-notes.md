# Atelier 6 — Affectations, DTO et validation

## Operations

| Endpoint | Request DTO | Response DTO | Behavior |
|---|---|---|---|
| `POST /api/affectations/maintenance` | `AffectationMaintenanceRequestDto` (`maintenanceId`, `vehiculeId`) | `MaintenanceDto` | Loads both rows, assigns the owning `Maintenance.vehicule` side, saves maintenance and returns its identifiers and vehicle plate |
| `POST /api/affectations/equipement` | `AffectationEquipementRequestDto` (`vehiculeId`, `equipementId`) | `VehiculeDto` | Loads both rows, adds the equipment to the vehicle's owning `equipements` set, saves and returns a vehicle summary with its equipment IDs |

Both request DTOs use Jakarta Bean Validation: IDs are required (`@NotNull`) and must be positive (`@Positive`). The controller applies `@Valid`. Invalid body fields return 400 with the field messages; missing database records return 404. The assignment methods run transactionally so lazy relations can be mapped into response DTOs before the transaction closes.

## Swagger examples

Open `/api/swagger-ui.html`, expand the **Affectations** group and use **Try it out** on either endpoint.

Maintenance request:

```json
{
  "maintenanceId": 1,
  "vehiculeId": 1
}
```

Equipment request:

```json
{
  "vehiculeId": 1,
  "equipementId": 1
}
```

To verify validation, submit zero or a negative ID and expect 400. Use existing maintenance, vehicle and equipment identifiers for successful 200 responses. The Postman collection also contains these request examples.
