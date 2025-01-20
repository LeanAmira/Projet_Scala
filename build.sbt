// Version globale du projet
ThisBuild / version := "0.1.0-SNAPSHOT"

// Version globale de Scala utilisée
ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "Projet_Scala",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.17" % Test // Dépendance pour les tests
    )
  )
