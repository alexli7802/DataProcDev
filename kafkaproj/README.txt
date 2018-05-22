
// code generation for 'avro messages'
java -jar tools/avro-tools-1.8.2.jar compile schema schemas/gtpu_msg.avsc src/main/scala/

// run kafka consumer
java -Dconfig.file=conf/application.conf -cp target/scala-2.12/KafkaProducers-assembly-0.1.0-SNAPSHOT.jar consumers.BasicConsumer

// run kafka producer
java -Dconfig.file=conf/application.conf -cp target/scala-2.12/KafkaProducers-assembly-0.1.0-SNAPSHOT.jar producers.BasicProducer test 25
