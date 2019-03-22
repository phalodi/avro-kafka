package org.phalodi.avro

import java.util.Properties

import com.example.examplepub.event.Person
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import org.rogach.scallop.ScallopConf

object AvroKafkaProducer {

  //get kafka url and registry url or use default if not specified
  private class Args(args: Array[String]) extends ScallopConf(args) {
    val kafkaUrl = opt[String](required = true, default = Some("http://localhost:9092"))
    val registryUrl = opt[String](required = true, default = Some("http://localhost:8081"))
    verify()
  }

  def main(args: Array[String]): Unit = {
    //command line args
    val cl = new Args(args)

    //producer properties
    val producerProps: Properties = new Properties()

    //set key serializer
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getCanonicalName)

    //set value serializer to avro serializern
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer].getCanonicalName)

    //set kafka url
    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, cl.kafkaUrl.toOption.get)

    //set schema registry url
    producerProps.put("schema.registry.url", cl.registryUrl.toOption.get)

    //create producer with type [String, ExampleRecord]
    val producer = new KafkaProducer[String, Person](producerProps)

    //publish 5 messages
    for (i <- 1 to 5) {
      producer.send(new ProducerRecord("person-topic", Person(s"person$i", "available")))
      println("publishing message " + i.toString)
    }

    //close producer
    producer.close()
  }
}
