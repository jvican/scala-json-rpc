lazy val commonSettings = Seq(
    scalaVersion := "2.12.3",
    scalacOptions in Compile ++= Seq(
      "-target:jvm-1.8",
      "-encoding",
      "UTF-8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xfuture",
      "-Xlint",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-inaccessible",
      "-Ywarn-infer-any",
      "-Ywarn-nullary-override",
      "-Ywarn-nullary-unit",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused",
      "-Ywarn-unused-import"
    )
  ) ++
    addCommandAlias("validate", ";scalafmtTest; coverage; test; coverageReport") ++
    addCommandAlias("validateAggregate", ";coverageAggregate")

lazy val publishSettings = Seq(
  homepage := Some(url("https://github.com/dhpcs/scala-json-rpc/")),
  startYear := Some(2015),
  description := "A Scala library providing types and JSON format typeclass instances for JSON-RPC 2.0 messages along with support for marshalling application level commands, responses and notifications via JSON-RPC 2.0.",
  licenses += "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt"),
  organization := "me.vican.jorge",
  organizationHomepage := Some(url("https://www.dhpcs.com/")),
  organizationName := "dhpcs",
  developers := List(
    Developer(
      id = "dhpiggott",
      name = "David Piggott",
      email = "david@piggott.me.uk",
      url = url("https://dhpiggott.net/")
    )),
  scmInfo := Some(
    ScmInfo(
      browseUrl = url("https://github.com/dhpcs/scala-json-rpc/"),
      connection = "scm:git:https://github.com/dhpcs/scala-json-rpc.git",
      devConnection = Some("scm:git:git@github.com:dhpcs/scala-json-rpc.git")
    )),
  bintrayRepository := "releases",
  bintrayPackageLabels := Seq("scala", "json-rpc")
)

lazy val noopPublishSettings = Seq(
  publish := {},
  publishLocal := {}
)

lazy val playJson = "com.typesafe.play" %% "play-json" % "2.6.3"

lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"

lazy val playJsonTestkit = project
  .in(file("play-json-testkit"))
  .settings(commonSettings)
  .settings(publishSettings)
  .settings(
    name := "play-json-testkit"
  )
  .settings(
    libraryDependencies ++= Seq(
      playJson,
      scalaTest
    ))

lazy val scalaJsonRpc = project
  .in(file("scala-json-rpc"))
  .settings(commonSettings)
  .settings(publishSettings)
  .settings(
    name := "scala-json-rpc"
  )
  .settings(libraryDependencies ++= Seq(
    playJson
  ))
  .dependsOn(playJsonTestkit % Test)
  .settings(libraryDependencies ++= Seq(
    scalaTest % Test
  ))

lazy val playJsonRpc = project
  .in(file("play-json-rpc"))
  .settings(commonSettings)
  .settings(publishSettings)
  .settings(
    name := "play-json-rpc"
  )
  .dependsOn(scalaJsonRpc)

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .settings(noopPublishSettings)
  .settings(
    name := "scala-json-rpc-root"
  )
  .aggregate(
    playJsonTestkit,
    scalaJsonRpc,
    playJsonRpc
  )
