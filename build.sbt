organization := "com.example"
version := "0.0.1"
scalaVersion := "2.11.7"

enablePlugins(PlayScala, SbtWeb)

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-encoding", "UTF-8",
  "-Xfuture",
  "-Xfatal-warnings"
)

scalacOptions in Test ++= Seq("-Yrangepos")

javaOptions ++= Seq(
  "-Xms512M",
  "-Xmx2048M",
  "-XX:MaxPermSize=1024M",
  "-XX:+CMSClassUnloadingEnabled",
  "-XX:+UseConcMarkSweepGC"
)

val kamonVersion = "0.6.1"
libraryDependencies ++= Seq(
  jdbc,
  cache,
  filters,

  "com.typesafe.play" %% "anorm" % "2.4.0",

  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.6",

  "org.postgresql" % "postgresql" % "9.3-1103-jdbc41",

  "com.softwaremill.macwire" %% "macros" % "2.1.0" % "provided",
  "com.softwaremill.macwire" %% "util" % "2.1.0",

  "org.specs2" %% "specs2-core" % "3.6.6" % "test",
  "org.specs2" %% "specs2-mock" % "3.6.6" % "test",
  "org.specs2" %% "specs2-matcher-extra" % "3.6.6" % "test",
  "org.specs2" %% "specs2-junit" % "3.6.6" % "test",
  "org.mockito" % "mockito-all" % "1.9.5" % "test",

  "org.spire-math" %% "cats" % "0.3.0",

  "io.kamon" %% "kamon-akka" % kamonVersion,
  "io.kamon" %% "kamon-play-24" % kamonVersion,
  "io.kamon" %% "kamon-log-reporter" % kamonVersion
)

includeFilter in (Assets, LessKeys.less) := "*.less"
excludeFilter in (Assets, LessKeys.less) := "_*.less"

routesGenerator := InjectedRoutesGenerator

//aspectjSettings
//javaOptions in run <++= AspectjKeys.weaverOptions in Aspectj
//fork in run := true