#/bin/bash

cp tests/systemTest/gsoDevTest/LeJOSTemplateTop.java $1
cp tests/systemTest/gsoDevTest/LeJOSTemplateBottom.java $1
cp tests/systemTest/gsoDevTest/LeJOSTemplateBottom.java $1
cp tests/systemTest/gsoDevTest/lejos_classes.jar $1
cat tests/systemTest/gsoDevTest/LeJOSTemplateTop.java $2 tests/systemTest/gsoDevTest/LeJOSTemplateBottom.java > $1/LeJOSBenchmark.java
javac $1/LeJOSBenchmark.java -cp $1/lejos_classes.jar

wc -c < $1/LeJOSBenchmark.class