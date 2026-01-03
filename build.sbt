name := """arif_play_test"""
organization := "arif_play_at_home"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.18"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "arif_play_at_home.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "arif_play_at_home.binders._"
