@ECHO off
CALL nxjc %2 > nul
CD %1
CALL nxjlink --output LeJOSFnTest.nxj LeJOSFnTest > nul
CALL nxjupload --run --usb LeJOSFnTest.nxj > nul
CALL nxjconsole > output.txt
FINDSTR "Number of failures" output.txt
DEL LeJOSFnTest.nxj
DEL LeJOSFnTest.class
DEL output.txt