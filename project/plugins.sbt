// Comment to get more information during initialization
logLevel := Level.Warn

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.1.0")

//Add the aspectj-play-runner plugin to project/plugins.sbt
// and run: aspectj-runner:run
addSbtPlugin("io.kamon" % "aspectj-play-runner" % "0.1.3")
//addSbtPlugin("com.typesafe.sbt" % "sbt-aspectj" % "0.10.6")