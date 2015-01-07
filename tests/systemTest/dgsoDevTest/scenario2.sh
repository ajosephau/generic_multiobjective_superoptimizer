#/bin/bash

cp tests/systemTest/dgsoDevTest/LeJOSTemplateTop.java $1
cp tests/systemTest/dgsoDevTest/LeJOSTemplateBottom.java $1
cp tests/systemTest/dgsoDevTest/LeJOSTemplateBottom.java $1
cp tests/systemTest/dgsoDevTest/lejos_classes.jar $1
cat tests/systemTest/dgsoDevTest/LeJOSTemplateTop.java $2 tests/systemTest/dgsoDevTest/LeJOSTemplateBottom.java > $1/LeJOSBenchmark.java
javac $1/LeJOSBenchmark.java -cp $1/lejos_classes.jar

wc -c < $1/LeJOSBenchmark.class