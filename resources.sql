

put file://./target/customreader-0.0.1-jar-with-dependencies.jar @mystage
auto_compress=False overwrite=True;


CREATE OR REPLACE FUNCTION CUSTOM_READ(filename String)
RETURNS TABLE(
-- customize return columns
inputPath String)
LANGUAGE JAVA
  RUNTIME_VERSION = '11'
  
  IMPORTS = ('@mystage/customreader-0.0.1-jar-with-dependencies.jar')
  PACKAGES = ('com.snowflake:snowpark:latest')
  HANDLER = 'custom.CustomFileReader';