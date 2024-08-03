ThisBuild / version := "1"

ThisBuild / scalaVersion := "2.12.19"

lazy val root = (project in file("."))
  .settings(
    name := "MOOD",
    libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.192-R14"
  )

