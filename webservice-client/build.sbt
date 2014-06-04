import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._
import spray.revolver.RevolverPlugin._

organization  := "com.waid0"

version       := "0.1"

//scalaVersion  := "2.11.1"
scalaVersion := "2.10.3"

packageArchetype.java_application

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.1.4"
  val sprayV = "1.1.1"
  val cxfV = "2.7.8"
  Seq(
  	"org.scala-lang"   % "scala-reflect" % "2.10.3",
    "io.spray"            %   "spray-can"     % sprayV,
    "io.spray"            %   "spray-routing" % sprayV,
    "io.spray" %%  "spray-json" % "1.2.5",
    "io.spray"            %   "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-slf4j"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "ch.qos.logback"      % "logback-classic" % "0.9.28" % "runtime",
    "org.apache.cxf"      %  "cxf-rt-frontend-jaxws" % cxfV,
    "org.apache.cxf"      %  "cxf-rt-transports-http" % cxfV,
    "commons-beanutils"   %  "commons-beanutils" % "1.8.3",
    "org.specs2"          %%  "specs2"        % "2.2.3" % "test"
  )
}

mainClass in Revolver.reStart := Some("com.waid.Main")

Revolver.enableDebugging(port = 5050, suspend = true)

Revolver.settings
