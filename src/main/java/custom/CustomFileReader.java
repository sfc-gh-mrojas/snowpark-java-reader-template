package custom;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.stream.Stream;

import com.snowflake.snowpark_java.types.SnowflakeFile;
import java.io.FileOutputStream;



class DataRow {
    public String column1;
    public String column2;
    public String column3;
    public String column4;	
    public String inputPath;

    public DataRow() {
        // Empty Constructor
    }

    public DataRow(String column1, String column2, String column3, String column4) {

    }

    public static String replaceNull(String inStr) {
		if (inStr != null && inStr.equalsIgnoreCase("null"))
			return "";
		else
			return inStr;
	}

    public String toString() {
        var sb = new StringBuilder();
        try {
            for(var f :this.getClass().getFields())
            {
                sb.append(f.getName());
                sb.append(":");
            
                    sb.append(f.get(this));

                sb.append(",");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}

class Reader {
    InputStream inputStream;
    DataRow     currentDataRow;
    public Reader(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    public boolean next()  {
        // Insert your logic here to process a custom file
        return false;
    }
    public DataRow get() {
        return currentDataRow;
    }
}

public class CustomFileReader {

    public static Class getOutputClass() { return DataRow.class; }

    
    public Stream<DataRow> process(String path)  {
        try  
        {
            InputStream inputStream = SnowflakeFile.newInstance(path, false).getInputStream();
            var reader = new Reader(inputStream);
            Stream<DataRow> resultStream = Stream.generate(() -> {
                if (reader.next())
                    return reader.get();
                else
                    return null; 
            }).takeWhile(Objects::nonNull);
            return resultStream;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    

    public static void main(String[] args) throws FileNotFoundException {
        String uri = args[0];
        var reader = new CustomFileReader();
        reader.process(uri).forEach(System.out::println);
    }

}