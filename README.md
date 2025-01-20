# Projet Scala : Scheduler DSL

## **Aperçu du Projet**
Ce projet est une implémentation d'un **Domain-Specific Language (DSL)** en Scala 3 pour la planification et l'exécution d'événements. Le DSL permet de définir des actions planifiées avec des horaires spécifiques de manière concise et intuitive.

---

## **Fonctionnalités Principales**
1. Ajouter des événements avec une action et une heure donnée.
2. Lister et exécuter les événements dans l'ordre chronologique.
3. Rechercher un événement spécifique par son action.
4. Incrémenter une heure avec un intervalle donné (e.g., `08:00` + `01:30` = `09:30`).

---

## **Exemple d'Utilisation**

```scala
val dsl = new DSL

dsl.simulate {
  dsl.event("Start coffee machine").at("08:00")
  dsl.event("Turn on the light").at("08:05")
  dsl.event("Play music").at("08:10")
}

Scheduler.run()
# Projet_Scala