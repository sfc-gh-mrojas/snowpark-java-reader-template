# Snowpark File Reader Template


It implements an UDTF (User Defined Table Function) that reads
a File and returns its content as rows.

# Build

```
mvn clean package   
```

That will produce a jar in the target directory. For example: `target/customreader-0.0.1-jar-with-dependencies.jar`

# Usage

Upload the jar to your Snowflake account, into a selected stage.
For example if your stage is named `mystage`:

You can register the UDTF like:

```
CREATE OR REPLACE FUNCTION CUSTOM_READ(filename String)
RETURNS TABLE(
column1,
column2,
...)
LANGUAGE JAVA
  RUNTIME_VERSION = '11'
  IMPORTS = ('@mystage/customreader-0.0.1-jar-with-dependencies.jar')
  PACKAGES = ('com.snowflake:snowpark:latest')
  HANDLER = 'custom.FileReader';

```

To use the UDTF:

```sql
select * from table(CUSTOM_READ('@mystage/sample1.bin'))
```
