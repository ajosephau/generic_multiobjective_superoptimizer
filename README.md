# generic_superoptimizer
An experimental project to develop a generic superoptimizer. That is, a superoptimizer that works with any programming language that can be expressed in a context-free grammar.

# Building GSO

* Run the Ant job "all" in the Apache Ant build file "generic_superoptimizer.xml", and a set of jar files will be created in the out\artifacts\generic_superoptimizer_jar folder.
* If you change the ANTLR v4 grammar (in the conf/ANTLRv4Grammar folder), you will need to run the "create.module.gso.antlrv4parser" task in the "compile_antlrv4_parser_from_g4_file.xml" to regenerate the java files used to parse the ANTLR v4 grammar files.

# Running GSO

GSO requires a single properties file to configure paths to required settings. Two example properties files: "conf/properties/config_gso_test_mac.properties" or "conf/properties/config_gso_test_win.properties" are included.

From IntelliJ Idea:
* Import project into IDE (I'm using Windows 8/ Mac OSX Yosemite with IntelliJ IDEA version 14).
* Set project to use Java 8 (ensure Java 8 is also available by checking that running "java" or "javac" works).
* run "Main.class" with a single parameter: a relative/absolute path to a properties file.

From above "Building GSO" step:
* In the folder containing the "generic_superoptimizer.jar" file, run "java -jar generic_superoptimizer.jar PROPERTIES_FILE_PATH" where PROPERTIES_FILE_PATH is the path to the properties file mentioned previously.

The "gso_test" app generates a small subset of Java basic primitive functions (see tests/grammars/gsoDevTest/java_lejos_superoptimizer_training.g4) and the "Test" sees if the function contains "VariableA". The scenarios determine the number of characters in the statements generated, and the size of the class when the statements generated is included in an example program.

# Licences

In addition to this program's licence, the following libraries used have the associated licences:
* ANTLR version 4: BSD Licence (http://www.antlr.org/license.html) 
* ANTLR v4 Grammar: BSD or MIT Licence (https://github.com/antlr/grammars-v4)
* Freemarker: Apache version 2.0 License (http://freemarker.org/docs/app_license.html)
* Log4j: Apache version 2.0 License (http://logging.apache.org/log4j/2.x/license.html)
* Apache Commons (Exec, IO and Lang3): Apache version 2.0 License (http://commons.apache.org/)
