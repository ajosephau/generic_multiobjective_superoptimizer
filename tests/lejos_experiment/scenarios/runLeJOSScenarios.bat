@ECHO OFF
CALL nxjc %2 > nul
CD %1
CALL nxjlink --output LeJOSPerfScen.nxj LeJOSPerfScen > nul
CALL nxjupload --run --usb LeJOSPerfScen.nxj > nul
CALL :sleep 5
CALL nxjconsole > nxjConsoleOutput.txt
FOR /f "delims=" %%a IN ('..\..\getFileSize.bat LeJOSPerfScen.nxj') DO @SET fileSize=%%a
FOR /f "delims=" %%a IN ('..\..\getComputationTime.bat') DO @SET computationTime=%%a
ECHO %fileSize% %computationTime%
DEL LeJOSPerfScen.class
DEL LeJOSPerfScen.nxj
DEL nxjConsoleOutput.txt
CALL :sleep 10

REM from http://superuser.com/questions/48231
:sleep
ping 127.0.0.1 -n 2 -w 1000 > NUL
ping 127.0.0.1 -n %1 -w 1000 > NUL