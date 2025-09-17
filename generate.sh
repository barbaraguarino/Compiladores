#!/bin/bash
set -e
java -jar tools/jflex-full-1.9.1.jar src/main/java/org/uff/Calc.flex
mkdir -p out
javac -d out src/main/java/org/uff/*.java
java -cp out org.uff.Main src/main/exemplos/teste.calc
# java -cp out org.uff.Main src/main/exemplos/teste.calc | tee src/main/exemplos/saida_calc.txt
