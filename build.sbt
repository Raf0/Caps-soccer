scalaVersion := "2.9.2"

addSbtPlugin("com.github.philcali" % "sbt-lwjgl-plugin" % "3.1.4")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0.M5b" % "test"

seq(lwjglSettings: _*)
