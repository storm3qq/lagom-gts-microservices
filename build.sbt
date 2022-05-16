
//=======[ Common Information Setting ]=========
ThisBuild / version := "v1.0.0"
ThisBuild / organization := "com.uet"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-java8-compat" % VersionScheme.Always

//=======[ Libraries ]==========================
val macwire = "com.softwaremill.macwire" %% "macros" % "2.5.7" % "provided"
val akkaClusterSharding = "com.typesafe.akka" %% "akka-cluster-sharding-typed" % "2.6.19"

//=======[ Module Setting ]=====================
lazy val `lagom-gts-microservices` = (project in file("."))
  .settings(
    name := "lagom-gts-microservices"
  )
  .aggregate(`gts-core-api`)

lazy val `gts-core-api` = (project in file("gts-core-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `gts-core-impl` = (project in file("gts-core-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      macwire, akkaClusterSharding
    )
  )
  .dependsOn(`gts-core-api`)
