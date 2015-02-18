@ECHO OFF
CALL nxjc %2 > nul
CD %1
CALL nxjlink --output LeJOSPerfScen.nxj LeJOSPerfScen > nul
CALL nxjupload --run --usb LeJOSPerfScen.nxj > nul
CALL nxjconsole > nxjConsoleOutput.txt
FOR /f "delims=" %%a IN ('..\..\getFileSize.bat LeJOSPerfScen.nxj') DO @SET fileSize=%%a
FOR /f "delims=" %%a IN ('..\..\getComputationTime.bat') DO @SET computationTime=%%a
ECHO %fileSize% %computationTime%
DEL LeJOSPerfScen.class
DEL LeJOSPerfScen.nxj
DEL nxjConsoleOutput.txt
