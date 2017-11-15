@echo off

cd D:\\MigrationTools\\TransformationTools\\
cmd /c RunTransformationTools.bat

cd D:\\MigrationTools\\TransformationTools\\output\\
cmd /c D:\\MigrationTools\\TransformationTools\\output\\DeployOutputFile.bat

cd D:\\MigrationTools\\ECBDataMigration\\Bin\\
cmd /c DeployBin.bat

cd D:\\MigrationTools\\ECBDataMigration\\Mapper\\
cmd /c RunMapper.bat

cd D:\\MigrationTools\\ECBDataMigration\\Mapper\\output\\
cmd /c DeployOutputFile.bat

cd D:\\MigrationTools\\ECBDataMigration\\Loader\\
cmd /c RunLoader.bat