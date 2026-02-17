name := "Lunar2026"
version := "0.1"
scalaVersion := "3.3.1" // 或者你使用的版本

// 如果需要图形库，在这里添加依赖
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

scalacOptions ++= Seq("-encoding", "utf8")
