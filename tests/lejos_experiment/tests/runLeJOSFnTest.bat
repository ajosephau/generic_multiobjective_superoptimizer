@ECHO OFF
XCOPY /Y /E /Q tests\lejos_experiment\tests\lejos_simulation_classes %1 > nul
CD %1
JAVAC LeJOSFnTest.java
JAVA LeJOSFnTest