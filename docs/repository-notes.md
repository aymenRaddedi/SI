# Atelier 3 — Spring Data JPA repositories

## Repositories delivered

All nine AutoLoc entities have a repository in `tn.esprit.autoloc.repository`. Each extends `JpaRepository<Entity, Long>`, giving the application CRUD operations, list-returning reads, paging, sorting, and JPA-specific flush operations. Spring Data creates the repository proxies at startup, so no handwritten implementation or `@Repository` annotation is needed.

| Entity | Repository | Reason |
|---|---|---|
| Agence | `IAgenceRepository` | Full CRUD, list reads, paging and sorting |
| Employe | `IEmployeRepository` | Full CRUD, list reads, paging and sorting |
| Vehicule | `IVehiculeRepository` | Full CRUD, list reads, paging and sorting |
| Equipement | `IEquipementRepository` | Full CRUD, list reads, paging and sorting |
| Client | `IClientRepository` | Full CRUD, list reads, paging and sorting |
| Reservation | `IReservationRepository` | Full CRUD, list reads, paging and sorting |
| Contrat | `IContratRepository` | Full CRUD; regular deletes preserve JPA cascades |
| Paiement | `IPaiementRepository` | Direct reads; business writes go through Contrat |
| Maintenance | `IMaintenanceRepository` | Full CRUD, list reads, paging and sorting |

`JpaRepository` is appropriate for this application because the services need regular CRUD plus `List` results and paging/sorting. `CrudRepository` exposes basic CRUD and returns `Iterable` for multi-row methods. `ListCrudRepository` has the same CRUD surface with `List` returns. `PagingAndSortingRepository` supplies paging and sorting; since Spring Data 3 it does not itself include CRUD. `Repository` is a marker interface from which only selected method signatures can be exposed.

Use regular entity deletion for Contrat. Batch deletion bypasses the persistence context and therefore does not apply JPA cascade or `orphanRemoval` behavior for its Paiement children.

## CRUD observations

- `save` persists a new entity when its generated identifier is null. For a non-null identifier, JPA may merge it; use the returned instance.
- `findById` returns `Optional<Entity>` and callers should handle absence explicitly.
- `deleteById` does not report an absent identifier in the current Spring Data API; the service layer must check existence when absence should be an error.
- A regular delete of Contrat passes through JPA and applies its configured cascade to Paiement.

## Quality review

The repository interfaces contain only the required Spring Data type declarations; no redundant `@Repository` stereotype or handwritten proxy implementation was added. Entity Lombok usage is targeted (`@Getter`, `@Setter`, constructors), avoiding `@Data` on JPA entities.

The SonarQube for IDE plugin is an IDE-side analyzer and is not configured in this environment, so no plugin findings are claimed as executed. Review the packages in IntelliJ with the plugin installed and record any environment-specific findings here.

## Startup verification

Spring Boot scans from `tn.esprit.autoloc`; these repositories are in its `repository` subpackage. At startup Spring Data should report nine JPA repository interfaces. Database startup additionally requires a reachable MySQL instance matching `application.properties`.
