# order-service


1)Prompt Command -> navigate to a root of the "order-service" project, follow the steps below to compile and run the program

2)Compile the files by running the command below:

javac -d bin/ -cp src src/main/java/com/br/gft/order_service/util/*.java src/main/java/com/br/gft/order_service/enumeration/*.java src/main/java/com/br/gft/order_service/exception/*.java src/main/java/com/br/gft/order_service/*.java -classpath commons-lang3-3.7.jar

3)Run the program using the following command line: java -cp bin;commons-lang3-3.7.jar com.br.gft.order_service.OrderController