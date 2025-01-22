// Version globale du projet
ThisBuild / version := "0.1.0-SNAPSHOT"

// Version globale de Scala utilisée
ThisBuild / scalaVersion := "3.3.4"

// Définir le projet principal
lazy val root = (project in file("."))
  .settings(
    name := "Projet_Scala",
    Compile / mainClass := Some("Main"), // Définir Main comme point d'entrée
    Test / testOptions += Tests.Argument("-oD"), // Afficher les résultats des tests de manière détaillée
    run := {
      (Test / test).value // Exécuter les tests lors de sbt run
      println("Tests fonctionnels exécutés avec succès.")
    },
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.17" % Test, // Add ScalaTest for testing
      "dev.zio" %% "zio" % "2.0.0",                      // Add ZIO core
      "dev.zio" %% "zio-streams" % "2.0.0", 
        "dev.zio" %% "zio-test" % "2.0.15" % Test,
        "dev.zio" %% "zio-test-sbt" % "2.0.15" % Test
    )
  )