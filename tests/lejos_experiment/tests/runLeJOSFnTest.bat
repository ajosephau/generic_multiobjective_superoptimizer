@ECHO off
CALL nxjc %2 > nul
CD %1
CALL nxjlink --output LeJOSFnTest.nxj LeJOSFnTest > nul
CALL nxjupload --run --usb LeJOSFnTest.nxj > nul
CALL :sleep 5
CALL nxjconsole > output.txt
FINDSTR "Number of failures" output.txt
DEL LeJOSFnTest.nxj
DEL LeJOSFnTest.class
DEL output.txt
CALL :sleep 10

REM from http://superuser.com/questions/48231
:sleep
ping 127.0.0.1 -n 2 -w 1000 > NUL
ping 127.0.0.1 -n %1 -w 1000 > NUL