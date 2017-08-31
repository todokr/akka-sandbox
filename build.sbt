name := "akka-sandbox"

organization := "io.github.todokr"

version := "0.1.0"

scalaVersion := "2.11.8"

resolvers := Seq(
      "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
      Resolver.sonatypeRepo("releases"),
      DefaultMavenRepository)

val akkaVersion = "2.4.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
  )

initialCommands := "import io.github.todokr._"

