# Projet Scala : Scheduler DSL

## **Aperçu du Projet**
Ce projet est une implémentation d'un **Domain-Specific Language (DSL)** en Scala 3 pour la planification et l'exécution d'événements. Le DSL permet de définir des actions planifiées avec des horaires spécifiques de manière concise et intuitive. Nous avons utilisé ZIO pour les fonctionnalités de programmation asynchrone et concurrente.

---

## **Fonctionnalités Principales**

1. Ajouter des événements avec une action et une heure spécifique
2. Planifier des événements récurrents (heures fixes, quotidienne ou hebdomadaire)
3. Lister et exécuter les événements dans un ordre chronologique.
4. Gérer plusieurs appareils IoT (machine à café, lumières, lecteur de musique et aspirateur électrique)
5. Supprimer ou rechercher un événement par son action
6. Définir des délais et incrémenter les heures

---

## **Conception et Choix techniques**

## **DSL**

```scala
val light = Light("light-1", "Living Room")
  .scheduleEvents { device =>
    device.turnOn().daily(LocalTime.of(8, 0))
    device.turnOff().daily("23:00")
}
```

## **Programmation Fonctionnelle**
  - Immutabilité : Les événements sont gérés de manière immuable.
  - Sécurité des types : Les horaires et récurrences sont fortement typés.
  - Composition : Les événements sont définis comme des effets asynchrones composables avec ZIO.

## **ZIO pour la Concurrence**
  - ZIO Streams permet de planifier et d'exécuter les événements en parallèle tout en gérant les délais et récurrences.
  - Les événements récurrents utilisent des schedules pour leur exécution.

## **Exemple d'Utilisation**
```scala
val light = Light("light-1", "Living Room")
  .scheduleEvents { device =>
    device.turnOn().daily(LocalTime.of(8, 0))
    device.turnOff().daily("23:00")
  }

val coffeeMachine = CoffeeMachine("coffee-machine-1")
coffeeMachine.scheduleEvents { device =>
  device.makeCoffee().at(ZonedDateTime.now().plusSeconds(15))
}

val musicPlayer = MusicPlayer("music-player-1").scheduleEvents { device =>
  device.playSong("Bohemian Rhapsody").at(ZonedDateTime.now().plusSeconds(20))
  device.playSong("Leave Me Alone").at(ZonedDateTime.now().plusSeconds(30))
}

val vacuum = RobotVacuum("vacuum-1")
  .scheduleEvents { device =>
    device.vacuum().weekly(DayOfWeek.WEDNESDAY, "23:00")
    device.stopVacuum().weekly(DayOfWeek.WEDNESDAY, "23:10")
  }

Home(List(light, coffeeMachine, musicPlayer, vacuum)).start()
```

## **Installation et Exécution**

Prérequis :

- Scala 3.3.0 ou plus récent
- ZIO 2.x
- Un environnement de développement compatible (comme IntelliJ IDEA)

Instructions

1. Clonez le dépôt du projet
```scala
git clone https://github.com/LeanAmira/Projet_Scala.git
```

2. Lancez le projet avec SBT
```scala
sbt run
```


## **Structure du projet**

### Modules Principaux : 

- dsl.IoTDSL : Fournit le DSL pour planifier et exécuter les événements.
- devices : Implémente les appareils IoT supportés (lumières, aspirateur, machine à café, etc.).
- Main : Point d’entrée du programme.
  
```scala
src/
  main/
    scala/
      dsl/
        IoTDSL.scala
      devices/
        CoffeeMachine.scala
        Light.scala
        MusicPlayer.scala
        RobotVacuum.scala
      Main.scala
  test/
    scala/
      SchedulerTest.scala
      FunctionalTest.scala
```
