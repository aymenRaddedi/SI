# Atelier 4 — IoC, injection de dependances et services

## A. Injection par constructeur et par attribut

| Mode | Champ final | Dependances visibles | Instanciation hors conteneur | Choix AutoLoc |
|---|---|---|---|---|
| Constructeur (`@RequiredArgsConstructor`) | Oui | Oui, dans le constructeur genere | Possible en fournissant les dependances | Retenu: objet complet et dependances obligatoires explicites |
| Attribut (`@Autowired`) | Non | Cachees dans la classe | Difficile; l'objet reste incomplet sans Spring | Non retenu; SonarQube for IDE signale generalement ce style |

Spring cree les beans `@Service` au demarrage et fournit les implementations des interfaces repository. Les services sont des singletons sans etat modifiable par requete.

## B. Services et dependances

| Service | Dependance | Injection | Regle metier |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Nom, ville et adresse requis |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Nom, prenom et role requis |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Attributs requis; tarif journalier non negatif |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Libelle requis |
| ClientServiceImpl | IClientRepository | Constructeur | Nom, prenom, email, numero de permis et date requis |
| ReservationServiceImpl | IReservationRepository | Constructeur | Dates requises; date de fin non anterieure au debut |
| ContratServiceImpl | IContratRepository | Constructeur | Date requise; montant total non negatif; mise a jour preserve les paiements |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Lecture seule; creation et suppression via Contrat |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Date de debut requise; fin non anterieure au debut |

Les operations de creation refusent un identifiant fourni. Les consultations et suppressions absentes produisent `ResourceNotFoundException`. Les mises a jour chargent l'entite existante avant de recopier les champs modifiables, afin de preserver les associations de composition/inverses.

## C. Messages d'erreur du conteneur

1. `NoSuchBeanDefinitionException` pour `IContratService`: verifier `@Service` sur `ContratServiceImpl`, l'implementation de l'interface et son placement sous `tn.esprit.autoloc`.
2. Deux beans injectables: placer `@Primary` sur l'implementation par defaut, ou mettre `@Qualifier("nomDuBean")` sur le parametre du constructeur qui doit etre choisi.
3. Une dependance circulaire signifie que les deux beans ont besoin l'un de l'autre pendant leur construction. L'injection constructeur rend le cycle visible au demarrage; extraire la responsabilite partagee dans un troisieme service evite le cycle.

## D. Qualite

Aucune execution de SonarQube for IDE n'est disponible dans cet environnement, donc aucun resultat de scan n'est presente comme effectue. Points de revue traites dans le code: injection par attribut evitee au profit des champs `final` et du constructeur; exception generique evitee au profit de `ResourceNotFoundException`; champs d'identifiant exclus des mises a jour; collections de composition/inverses preservees pendant les mises a jour de Contrat, Agence, Client, Reservation et Vehicule.

## E. Questions de comprehension

1. Le conteneur Spring cree `ContratServiceImpl` pendant le scan de composants et lui injecte le proxy `IContratRepository`.
2. Le controleur depend de `IContratService` pour rester couple au contrat et permettre de remplacer l'implementation.
3. Un singleton sert plusieurs requetes: stocker un contrat courant dans un champ ferait partager cet etat entre utilisateurs.
4. Charger puis recopier evite d'ecraser des champs absents et les paiements en cascade/orphan removal.
5. `@Component` declare un composant detecte par scan; `@Bean` declare explicitement le resultat d'une methode `@Configuration`. `@Primary` choisit le candidat par defaut; `@Qualifier` choisit explicitement un candidat a un point d'injection. Paiement reste en lecture seule parce que Contrat gere la composition et ses cascades.
