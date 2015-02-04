@echo OFF

REM setup output variables
SET false_text="false test did not find variableA"
SET true_text="true test found variableA"

REM check if file contains the "variableA" text"
type %2 | find /c "variableA" > %1\out.txt
SET /p output=<%1\out.txt
DEL %1\out.txt
REM ECHO %output:"=%

IF %output%== 0 (
  echo %false_text:"=%
  exit /b 1
) else (
  echo %true_text:"=%
  exit /b 0
)

exit /b 1
