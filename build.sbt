//=======[ Common Information Setting ]=========
ThisBuild / version := "v1.0.0"
ThisBuild / organization := "com.uet"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / lagomServiceLocatorPort := 10000
ThisBuild / lagomCassandraEnabled := false
ThisBuild / scalacOptions ++= List("-encoding", "utf8", "-deprecation", "-feature", "-unchecked", "-Xfatal-warnings")

//=======[ Libraries ]==========================
val macwire = "com.softwaremill.macwire" %% "macros" % "2.5.7" % "provided"
val akkaClusterSharding = "com.typesafe.akka" %% "akka-cluster-sharding-typed" % "2.6.19"
val akkaDiscovery = "com.typesafe.akka" %% "akka-discovery" % "2.6.19"
val akkaJackson = "com.typesafe.akka" %% "akka-serialization-jackson" % "2.6.19"
val akkaStream = "com.typesafe.akka" %% "akka-stream-typed" % "2.6.19"
val slf4j = "com.typesafe.akka" %% "akka-slf4j" % "2.6.19"
val akkaDiscoveryKubernetesApi = "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api" % "1.1.3"

//=======[ Module Setting ]=====================
lazy val `lagom-gts-microservices` = (project in file("."))
  .settings(
    name := "lagom-gts-microservices"
  )
  .aggregate(`gts-core-api`, `gts-core-impl`)
  .enablePlugins(JavaAppPackaging)

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
      macwire, akkaClusterSharding, akkaDiscovery, akkaJackson, akkaStream,
      lagomScaladslCluster, slf4j, lagomScaladslAkkaDiscovery,
      akkaDiscoveryKubernetesApi, lagomScaladslPersistenceJdbc
    )
  )
  .dependsOn(`gts-core-api`)
  .settings(lagomServiceHttpPort := 11000)
