@echo off

cd D:\\Migration\\TransformationTools\\
cmd /c RunTransformationTools.bat

cd D:\\Migration\\TransformationTools\\output\\
cmd /c D:\\Migration\\TransformationTools\\output\\DeployOutputFile.bat

cd D:\\Migration\\ECBDataMigration\\Mapper\\
cmd /c RunMapper.bat

cd D:\\Migration\\ECBDataMigration\\Mapper\\output\\
cmd /c DeployOutputFile&Run.bat

