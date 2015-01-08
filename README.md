# generic_superoptimizer
An experimental project to develop a distributed, generic superoptimizer.

# Running GSO

* import project into IDE (I'm using OSX with IntelliJ IDEA version 14).
* set project to use Java 8 (ensure Java 8 is also available by checking that running "java" or "javac" works).
* run "Main.class" with the parameter "conf/properties/config_gso_test.properties".
* The "gso_test" app generates a small subset of Java basic primitive functions (see tests/grammars/gsoDevTest/java_lejos_superoptimizer_training.g4) and the "Test" sees if the function contains "VariableA". The scenarios determine the number of characters in the statements generated, and the size of the class when the statements generated is included in an example program.

# Licences

In addition to this program's licence, the following libraries used have the associated licences:
* ANTLR version 4: BSD Licence (http://www.antlr.org/license.html) 
* ANTLR v4 Grammar: BSD or MIT Licence (https://github.com/antlr/grammars-v4)
* Freemarker: Apache version 2.0 License (http://freemarker.org/docs/app_license.html)
* Log4j: Apache version 2.0 License (http://logging.apache.org/log4j/2.x/license.html)
* Apache Commons (Exec, IO and Lang3): Apache version 2.0 License (http://commons.apache.org/)
