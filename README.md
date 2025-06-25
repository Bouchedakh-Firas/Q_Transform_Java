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

## Pages Web

- `/` - Page d'accueil avec le titre "Acloud Quarter" et le logo
- `/joke` - Page du générateur de blagues aléatoires
- `/signature` - Page du générateur de signatures email

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
