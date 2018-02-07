@echo off

for %%f in (*) do ( 
 echo "name: %%~f"  
 cp %%~f D:\\MetraTech\\RMP\\Config\\SqlCore\\Queries\\PCWS\\%%~f 
)

rm D:\\MetraTech\\RMP\\Config\\SqlCore\\Queries\\PCWS\\DeployScript.bat