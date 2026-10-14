# Stratégie de fetch et de cascade : AutoLoc
| Association | Fetch | Cascade | Justification |
|---|---|---|---|
| Contrat → Paiement | LAZY | ALL + orphanRemoval | Un paiement n'existe que rattaché à son contrat (composition) : supprimer le contrat supprime ses paiements, et retirer un paiement de la liste le supprime en base. LAZY car on n'a pas besoin des paiements à chaque lecture d'un contrat. |
| Agence → Vehicule | LAZY | Aucune | Un véhicule survit à la suppression de son agence. LAZY évite de charger toute la flotte à chaque lecture d'une agence. |
| Agence → Employe | LAZY | Aucune | Un employé existe indépendamment de son agence (il peut être réaffecté). LAZY pour ne pas charger la liste du personnel inutilement. |
| Vehicule ↔ Equipement | LAZY | Aucune | Les équipements sont partagés entre plusieurs véhicules : supprimer un véhicule ne doit jamais supprimer un équipement utilisé ailleurs. Utilisation d'un Set pour éviter les doublons. |
| Client → Reservation | LAZY | PERSIST | Enregistrer un nouveau client avec ses réservations les enregistre aussi. Pas de REMOVE : l'historique des réservations ne doit pas disparaître silencieusement avec le client. |
| Reservation → Vehicule | LAZY | Aucune | Un véhicule existe indépendamment des réservations. ManyToOne forcé en LAZY (EAGER par défaut) pour éviter de charger toute la chaîne d'objets liés. |
| Reservation ↔ Contrat | LAZY | ALL côté Reservation | Le contrat dépend de sa réservation (cycle de vie lié). Côté inverse du OneToOne, Hibernate peut ignorer LAZY sans instrumentation du bytecode : limite connue de JPA. |
| Vehicule → Maintenance | LAZY | PERSIST | Une maintenance est créée avec le véhicule concerné, mais pas de REMOVE : l'historique de maintenance est conservé. |
## Principes retenus
- Tous les `@ManyToOne` sont forcés en LAZY (EAGER par défaut) pour éviter le chargement en cascade d'objets liés.
- Les collections `@OneToMany` et `@ManyToMany` restent en LAZY.
- Une cascade REMOVE n'est utilisée que pour une vraie composition (Contrat → Paiement, Reservation → Contrat).
- Pas de `@Data` sur les entités : `@Getter`/`@Setter` ciblés pour éviter les boucles infinies de `toString`/`hashCode`.