# Calculateur de Taxes de Vente

Ce projet est une simple application web qui calcule les taxes de vente pour une liste de produits selon des règles prédéfinies. L'application fournit une API REST pour générer des reçus à partir des entrées fournies et renvoie les détails formatés du reçu en sortie.

## Fonctionnalités

- Calculer la taxe de vente de base et le droit d'importation.
- Générer un reçu listant tous les articles avec leurs prix (taxes comprises), la taxe de vente totale et le coût total.
- Exclure certaines catégories de produits (livres, nourriture, produits médicaux) de la taxe de vente de base.
- Inclure un droit d'importation supplémentaire pour les biens importés.

## Prise en Main

### Prérequis

- Java 17
- Maven
- Un IDE tel qu'IntelliJ IDEA ou Eclipse

### Installation

1. Cloner le dépôt :
    ```bash
    git clone https://github.com/morobah/Taxe-pro
    cd Taxe-pro
    ```

2. Construire le projet en utilisant Maven :
    ```bash
    mvn clean install
    ```

3. Exécuter l'application :
    ```bash
    mvn spring-boot:run
    ```

## Utilisation

L'application expose un endpoint API REST pour générer des reçus. Vous pouvez utiliser `curl` ou tout autre client API pour envoyer des requêtes.

### Générer un Reçu

**Endpoint**: `POST /taxe/genererRecu`

**Corps de la Requête**: Une chaîne JSON contenant la liste des produits dans le format suivant :

