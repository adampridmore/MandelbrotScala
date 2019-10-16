// The simplest possible sbt build file is just one line:

scalaVersion := "2.12.8"

name := "mandelbrot"
organization := "ch.epfl.scala"
version := "1.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5",
  "org.typelevel" %% "cats-core" % "1.4.0",
  "org.scalanlp" %% "breeze" % "1.0",
  "org.scalanlp" %% "breeze-natives" % "1.0",
  "org.scalanlp" %% "breeze-viz" % "1.0",
  "org.scalafx" %% "scalafx" % "8.0.192-R14",
  "org.reactivemongo" %% "reactivemongo" % "0.18.7",
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
