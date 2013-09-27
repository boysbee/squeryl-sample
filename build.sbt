name := "squeryl-sample"

version := "1.0"

scalaVersion := "2.9.2"

unmanagedBase <<= baseDirectory { base => base / "lib" }

unmanagedJars in Compile <++= baseDirectory map { base =>
    val baseDirectories = (base / "lib")
    val customJars = (baseDirectories ** "*.jar")
    customJars.classpath
}



libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "1.9.1" % "test",
	"org.squeryl" %% "squeryl" % "0.9.5-6",
	"com.h2database" % "h2" % "1.2.127",
	"org.apache.derby" % "derby" % "10.7.1.1",
	"c3p0" % "c3p0" % "0.9.1.2",
	"org.slf4j" % "slf4j-log4j12" % "1.2"
)

resolvers ++= Seq(
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)