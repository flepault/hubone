@echo off

for %%f in (*) do ( 
 echo "name: %%~f"  
 cp %%~f D:\\MetraTech\\RMP\\Bin\\%%~f 
)

rm D:\\MetraTech\\RMP\\Bin\\DeployBin.bat