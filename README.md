# Acloud Quarter Web Application

Une application web Java 8 simple construite avec Spring Boot.

## Aperçu du projet

Ce projet démontre une application web Java 8 de base avec les fonctionnalités suivantes :
- Framework Spring Boot pour le développement web
- Architecture MVC (Modèles, Vues, Contrôleurs)
- Points d'accès API RESTful
- Templates Thymeleaf pour le rendu côté serveur
- Page d'accueil avec intégration API
- Interface utilisateur en français
- Logo personnalisable
- Test de l'API Google Places pour la recherche de restaurants

## Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── app/
│   │               ├── controllers/    # Contrôleurs Web et API
│   │               │   ├── ApiController.java  # Gère les requêtes API REST
│   │               │   └── WebController.java  # Gère les requêtes de pages web
│   │               ├── models/         # Modèles de données
│   │               │   ├── EmailSignature.java  # Modèle pour les signatures email
│   │               │   ├── Joke.java           # Modèle pour les blagues
│   │               │   └── Message.java        # Modèle pour les messages de bienvenue
│   │               ├── services/       # Classes de service
│   │               │   ├── JokeService.java    # Service pour générer des blagues aléatoires
│   │               │   └── SignatureService.java # Service pour générer des signatures email
│   │               └── Application.java # Classe principale de l'application
│   └── resources/
│       ├── static/                    # Ressources statiques (CSS, JS, images)
│       │   └── images/                # Images utilisées dans l'application
│       │       └── Acloud.png         # Logo de l'application
│       ├── templates/                 # Templates Thymeleaf
│       │   ├── index.html            # Page d'accueil
│       │   ├── joke.html             # Page de générateur de blagues
│       │   └── signature.html        # Page de générateur de signatures email
│       └── application.properties    # Configuration de l'application
└── test/
    └── java/                         # Classes de test
```

## Prérequis

- Java 8 JDK
- Maven 3.6+ ou outil de build compatible
- Clé API Google Maps valide pour les fonctionnalités de recherche de restaurants

## Configuration de l'API Google

Pour utiliser les fonctionnalités de recherche de restaurants, vous devez configurer une clé API Google Maps valide :

1. Obtenez une clé API Google Maps avec les API Places et Geocoding activées depuis la [Console Google Cloud](https://console.cloud.google.com/)
2. Ouvrez le fichier `src/main/resources/application.properties`
3. Remplacez `YOUR_GOOGLE_API_KEY` par votre clé API Google Maps :
   ```
   google.api.key=YOUR_ACTUAL_API_KEY_HERE
   ```

## Construction du projet

Pour construire le projet, exécutez :

```bash
mvn clean package
```

## Exécution de l'application

Pour exécuter l'application, utilisez :

```bash
mvn spring-boot:run
```

Ou après la construction :

```bash
java -jar target/java8-web-app-0.0.1-SNAPSHOT.jar
```

L'application sera disponible à l'adresse http://localhost:8080

## Points d'accès API

- `GET /api/welcome` - Renvoie un message de bienvenue avec un horodatage
  - Exemple de réponse : `{"content":"Bienvenue sur l'application web Acloud Quarter!","timestamp":"2023-06-15T10:30:45.123"}`

- `POST /api/signature` - Génère une signature email basée sur les paramètres fournis
  - Paramètres :
    - `firstName` - Prénom de la personne
    - `lastName` - Nom de famille de la personne
    - `jobTitle` - Titre du poste de la personne
  - Exemple de requête : `POST /api/signature?firstName=Jean&lastName=Dupont&jobTitle=Développeur`
  - Exemple de réponse : `{"signature":"Jean Dupont\nDéveloppeur\nAcloud Quarter\njean.dupont@example.com\n+33 1 23 45 67 89"}`

- `GET /api/joke` - Renvoie une blague aléatoire en français
  - Exemple de réponse : `{"content":"Pourquoi les scientifiques ne font-ils pas confiance aux atomes ? Parce qu'ils inventent tout !","category":"Science"}`

- `POST /api/restaurant/random` - Trouve un restaurant aléatoire près de l'adresse spécifiée qui correspond aux préférences alimentaires
  - Paramètres :
    - `address` - Adresse de l'utilisateur (obligatoire)
    - `dietaryPreferences` - Liste des préférences alimentaires (optionnel, peut être multiple)
    - `foodType` - Type de cuisine recherché (optionnel, un seul type peut être spécifié)
  - Exemple de requête : `POST /api/restaurant/random?address=123 Rue de Paris, 75001 Paris&dietaryPreferences=végétarien&foodType=italien`
  - Exemple de réponse : 
    ```json
    {
      "name": "Le Petit Bistro",
      "address": "123 Rue de Paris, 75001 Paris",
      "cuisineType": "Italien",
      "rating": 4.5,
      "distance": 0.8,
      "dietaryOptions": ["Végétarien", "Sans Gluten"],
      "phoneNumber": "+33 1 23 45 67 89",
      "website": "http://www.lepetitbistro.fr"
    }
    ```
  - Note : La recherche est limitée à un rayon de 1 km autour de l'adresse spécifiée.

- `POST /api/restaurant/test` - Teste l'API Google Places en trouvant le premier restaurant près de l'adresse spécifiée
  - Paramètres :
    - `address` - Adresse de l'utilisateur (obligatoire)
    - `foodType` - Type de cuisine recherché (optionnel)
  - Exemple de requête : `POST /api/restaurant/test?address=123 Rue de Paris, 75001 Paris&foodType=japonais`
  - Exemple de réponse : Similaire à `/api/restaurant/random` mais renvoie toujours le premier résultat de l'API Google Places
  - En cas d'erreur, renvoie un message d'erreur détaillé avec le code d'état HTTP approprié
  - Note : La recherche est limitée à un rayon de 1 km autour de l'adresse spécifiée.

## Pages Web

- `/` - Page d'accueil avec le titre "Acloud Quarter" et le logo
- `/joke` - Page du générateur de blagues aléatoires
- `/signature` - Page du générateur de signatures email
- `/restaurant` - Page du trouveur de restaurant aléatoire avec fonctionnalité de test de l'API

## Test de l'API Restaurant

La page `/restaurant` inclut maintenant une section de test de l'API qui permet de :

1. Entrer une adresse pour tester l'API Google Places
2. Voir le premier résultat retourné par l'API
3. Afficher les erreurs éventuelles en cas de problème avec l'API

Cette fonctionnalité est utile pour :
- Vérifier que votre clé API Google est correctement configurée
- Tester la connectivité avec l'API Google Places
- Voir les données brutes retournées par l'API

## Tests de Performance

La page `/performance` offre une suite complète de tests pour évaluer les performances de votre environnement Java. Ces tests sont particulièrement utiles pour comparer différentes versions de JDK ou configurations système.

### Types de Tests Disponibles

1. **Test CPU**
   - Calcule des nombres premiers jusqu'à 2 millions
   - Calcule le 40ème nombre de Fibonacci
   - Calcule la factorielle de 20
   - **Objectif**: Évaluer les performances de calcul pur et l'optimisation des algorithmes mathématiques

2. **Test Mémoire**
   - Alloue et manipule un tableau de 20 millions d'entiers (environ 80 Mo)
   - Crée et manipule une matrice 2D de 2000x2000
   - Crée et manipule une liste d'un million d'objets String
   - **Objectif**: Évaluer la gestion de la mémoire, l'allocation et la vitesse d'accès

3. **Test Concurrence**
   - Exécute plusieurs tâches en parallèle sur plusieurs threads
   - Calcule des nombres premiers dans différentes plages
   - Effectue des multiplications de matrices
   - Manipule des collections concurrentes (ConcurrentHashMap)
   - **Objectif**: Évaluer les performances multi-thread et la gestion de la concurrence

### Métriques Mesurées

Pour chaque test, les métriques suivantes sont mesurées et affichées:

- **Temps d'exécution**: Durée totale du test en millisecondes
- **Mémoire utilisée**: Quantité de mémoire consommée par le test en Mo
- **Processeurs disponibles**: Nombre de processeurs/cœurs détectés
- **Threads utilisés**: Nombre de threads utilisés pour le test
- **Informations supplémentaires**: Détails spécifiques sur les opérations effectuées

### Informations Système

La page affiche également des informations détaillées sur votre environnement:

- Nombre de processeurs disponibles
- Mémoire maximale allouée à la JVM
- Mémoire totale et libre
- Version Java et fournisseur
- Système d'exploitation

### Utilisation des Tests de Performance

Ces tests peuvent être utilisés pour:

1. **Comparer différentes versions de JDK**: Évaluer les améliorations de performance entre Java 8 et des versions plus récentes
2. **Optimiser les configurations**: Tester l'impact des paramètres JVM sur les performances
3. **Évaluer le matériel**: Comparer les performances sur différentes configurations matérielles
4. **Diagnostiquer des problèmes**: Identifier des goulots d'étranglement potentiels dans votre environnement

### Interprétation des Résultats

- **Test CPU**: Des temps d'exécution plus courts indiquent de meilleures performances de calcul
- **Test Mémoire**: Une utilisation mémoire efficace et des temps d'accès rapides sont préférables
- **Test Concurrence**: Des temps d'exécution plus courts avec un nombre élevé de threads indiquent une bonne gestion de la concurrence

## Personnalisation du logo

Le logo de l'application est stocké dans le répertoire `/src/main/resources/static/images/Acloud.png`. Pour changer le logo :

1. Préparez votre nouvelle image (de préférence au format PNG)
2. Remplacez le fichier existant à l'emplacement indiqué
3. Conservez le même nom de fichier (`Acloud.png`) ou mettez à jour les références dans les templates HTML si vous changez le nom

## Tests

Pour exécuter les tests :

```bash
mvn test
```

## Internationalisation

L'application est actuellement disponible en français. Les éléments suivants ont été traduits :
- Interface utilisateur des pages web
- Messages de l'API
- Contenu des blagues
