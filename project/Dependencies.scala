import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt._

object Dependencies extends Build {
  val udashVersion = "0.2.0"
  val udashJQueryVersion = "1.0.0"
  val logbackVersion = "1.1.3"
  val jettyVersion = "9.3.8.v20160314"
  val groovyVersion = "2.4.6"
  val scalacssVerion = "0.4.1"
  val akkaVersion = "2.4.4"
  val akkaMongoVersion = "1.2.5"
  val reactiveMongoVersion = "0.11.9"

  val crossDeps = Def.setting(Seq[ModuleID](
    "io.udash" %%% "udash-core-shared" % udashVersion,
    "io.udash" %%% "udash-rpc-shared" % udashVersion
  ))
  val frontendDeps = Def.setting(Seq[ModuleID](
    "io.udash" %%% "udash-core-frontend" % udashVersion,
    "io.udash" %%% "udash-jquery" % udashJQueryVersion,
    "io.udash" %%% "udash-rpc-frontend" % udashVersion,
    "com.github.japgolly.scalacss" %%% "core" % scalacssVerion,
    "com.github.japgolly.scalacss" %%% "ext-scalatags" % scalacssVerion
  ))

  val frontendJSDeps = Def.setting(Seq[org.scalajs.sbtplugin.JSModuleID](
  ))

  val backendDeps = Def.setting(Seq[ModuleID](
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "org.eclipse.jetty" % "jetty-server" % jettyVersion,
    "org.eclipse.jetty" % "jetty-servlet" % jettyVersion,
    "io.udash" %% "udash-rpc-backend" % udashVersion,
    "org.eclipse.jetty.websocket" % "websocket-server" % jettyVersion,
    "org.codehaus.groovy" % "groovy-all" % groovyVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
    "com.github.scullxbones" %% "akka-persistence-mongo-rxmongo" % akkaMongoVersion,
    "org.reactivemongo" %% "reactivemongo" % reactiveMongoVersion exclude("com.typesafe.play", "play-iteratees_2.11"),
    "com.typesafe.play" % "play-iteratees_2.11" % "2.3.10"
  ))
}