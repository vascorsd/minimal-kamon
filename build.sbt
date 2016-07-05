scalaVersion := "2.11.7"

lazy val minimal = (project in file("."))
  .settings(
     run := { (run in web in Compile).evaluated }
   )

lazy val web = (project in file("web"))
