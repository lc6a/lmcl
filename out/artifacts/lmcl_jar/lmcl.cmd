@echo off

set check_flag=true
if "true" == "%check_flag%" (
  java -version
)
if not %errorlevel% == 0 (
  echo Can not run java,check it.
  set JAVA=D:\app\java\jdk14\bin\java
) else (
  set JAVA=java
)

%JAVA% -jar lmcl.jar %*
pause