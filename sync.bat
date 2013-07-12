
@set ASSETS=%cd%\assets
@set SRC=%cd%\src
if not exist %SRC%\com	mkdir %SRC%\com
if not exist %SRC%\com\assetsres	mkdir %SRC%\com\assetsres
cd  %ASSETS%
java -jar loadAssetsRes.jar
xcopy %ASSETS%\AssertRes.java %SRC%\com\assetsres /y
pause