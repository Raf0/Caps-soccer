import sbt._
import Keys._

object MyBuild extends Build { 
    val buildOrganization  = "com.example"
    val buildVersion       = "0.1.0-SNAPSHOT"
    val buildScalaVersion  = "2.9.1"

    val rootProjectId = "myproject"

    val lwjglVersion = "2.9.3"

    object Settings {
        lazy val base = Defaults.defaultSettings ++ Seq(
            organization    := buildOrganization,
            version         := buildVersion,
            scalaVersion    := buildScalaVersion)
        
        lazy val lwjgl = LWJGLPlugin.lwjglSettings ++ Seq(
            LWJGLPlugin.lwjgl.version := lwjglVersion)
        
        lazy val rootProject = base ++ lwjgl
    }
	
    lazy val root = Project(id=rootProjectId, base=file("."), settings=Settings.rootProject)
}