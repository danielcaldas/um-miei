#!/bin/sh

export CLASSPATH=".:/usr/local/lib/antlr-4.4-complete.jar:$CLASSPATH"
export CLASSPATH=".:mysql-connector-java-5.1.38-bin.jar:$CLASSPATH"
export CLASSPATH=".:gestorAudicao.jar:$CLASSPATH"
#alias antlr4='java -jar /usr/local/lib/antlr-4.5.1-complete.jar'
alias grun="java org.antlr.v4.runtime.misc.TestRig"

# a4 audicao.g4
# javac audicao*.java
grun audicao audicao $1 > ../result.txt