rm src/scanner/Scanner.java
java -jar src/scanner/jflex-1.6.1.jar src/scanner/Scanner.jflex
javac -Xlint -d out/production/Compiler src/*/*.java
cd out/production/Compiler
jar cfe out.jar general.Main */*.class
mv out.jar ../../..
cd ../../..
java -jar out.jar testInput/fib.pas
