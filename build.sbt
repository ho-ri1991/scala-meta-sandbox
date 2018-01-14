import sbt.Keys._

val libVersion = "1.0"

val projectName = "scala-meta-sandbox"

val scala = "2.12.2"

crossScalaVersions := Seq("2.11.11", "2.12.2")

// https://github.com/scalameta/sbt-macro-example/blob/master/build.sbt
lazy val metaMacroSettings: Seq[Def.Setting[_]] = Seq(
  resolvers += Resolver.sonatypeRepo("releases"),
  resolvers += Resolver.bintrayIvyRepo("scalameta", "maven"),
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M9" cross CrossVersion.full),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
    "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided"
  ),
  scalacOptions ++= Seq(
    "-Xplugin-require:macroparadise",
    "-Ymacro-debug-lite"
  )
)

def commonSettings(prjName: String) = Seq(
  name := prjName,
  scalaVersion := scala,
  version := libVersion
)

lazy val root = (project in file("."))
  .aggregate(metaFunctions, metaMain)

lazy val metaFunctions = (project in file("./meta-functions"))
  .settings(commonSettings("meta-functions"))
  .settings(metaMacroSettings)
  .settings(libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0")// % "provided")

lazy val metaMain = (project in file("./meta-main"))
  .settings(commonSettings("meta-main"))
  .settings(metaMacroSettings)
  //    .settings(libraryDependencies += groupId %% projectName % libVersion % "provided")
  .dependsOn(metaFunctions)
