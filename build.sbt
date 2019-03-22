lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    name := "avro-kafka",
    version := "1.0.0",
    scalaVersion := "2.12.6",
    libraryDependencies ++= Seq("org.apache.kafka" % "kafka-clients" % "1.0.0",
      "io.confluent" % "kafka-avro-serializer" % "4.0.0",
      "org.apache.kafka" % "kafka-streams" % "1.0.0",
      "com.lightbend" %% "kafka-streams-scala" % "0.1.0",
      "org.rogach" %% "scallop" % "3.1.1",
      "org.apache.avro" % "avro" % "1.8.2",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.4",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.4",
      "com.jsuereth" %% "scala-arm" % "2.0",
      "com.julianpeeters" %% "avrohugger-core" % "1.0.0-RC15"),
    resolvers += "io.confluent" at "http://packages.confluent.io/maven/",
    sourceGenerators in Compile += (avroScalaGenerateSpecific in Compile).taskValue
  )
