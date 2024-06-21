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

L'application expose des endpoints API REST pour générer des reçus. Vous pouvez utiliser `curl` ou tout autre client API pour envoyer des requêtes.

### Générer un Reçu

**Endpoint**: `POST /taxe/genererRecu`

**Corps de la Requête**: Une chaîne JSON contenant la liste des produits dans le format suivant :

#### Exemples de Requêtes et Réponses

1. **Entrée 1:**
   - Requête:
       ```bash
       curl -X POST http://localhost:8080/taxe/genererRecu -H "Content-Type: application/json" -d '1 Book à 12.49\n1 Music CD à 14.99\n1 Chocolate Bar à 0.85'
       ```
   - Réponse attendue:
       ```yaml
       produits:
         - produit: Book
           quantité: 1
           prix: 12.49
         - produit: Music CD
           quantité: 1
           prix: 16.49
         - produit: Chocolate Bar
           quantité: 1
           prix: 0.95
       Taxes de vente: 1.50
       Total: 29.93
       ```

2. **Entrée 2:**
   - Requête:
       ```bash
       curl -X POST http://localhost:8080/taxe/genererRecu -H "Content-Type: application/json" -d '1 box of imported chocolates à 10.00\n1 imported bottle of perfume à 47.50'
       ```
   - Réponse attendue:
       ```yaml
       produits:
         - produit: box of imported chocolates
           quantité: 1
           prix: 10.50
         - produit: imported bottle of perfume
           quantité: 1
           prix: 54.65
       Taxes de vente: 7.65
       Total: 65.15
       ```

3. **Entrée 3:**
   - Requête:
       ```bash
       curl -X POST http://localhost:8080/taxe/genererRecu -H "Content-Type: application/json" -d '1 imported bottle of perfume à 27.99\n1 bottle of perfume à 18.99\n1 packet of headache pills à 9.75\n1 box of imported chocolates à 11.25'
       ```
   - Réponse attendue:
       ```yaml
       produits:
         - produit: imported bottle of perfume
           quantité: 1
           prix: 32.19
         - produit: bottle of perfume
           quantité: 1
           prix: 20.89
         - produit: packet of headache pills
           quantité: 1
           prix: 9.75
         - produit: box of imported chocolates
           quantité: 1
           prix: 11.85
       Taxes de vente: 6.70
       Total: 74.68
       ```

## Tests unitaires

Des tests unitaires ont été écrits pour assurer le bon fonctionnement de l'application. Vous pouvez exécuter les tests avec la commande suivante :

```bash
mvn test
