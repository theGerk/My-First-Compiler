cd ~/Source/School/Compilers/Compiler/out/production/Compiler
rm ../../../src/scanner/Scanner.java
java -jar ../../../src/scanner/jflex-1.6.1.jar ../../../src/scanner/Scanner.jflex
javac -Xlint -d . ../../../src/*/*.java
jar cfe out.jar general.Main */*.class

