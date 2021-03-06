# Utility  
Utility files used across multiple projects.  
The files in this project are often useful in other projects.  
  
## Java  
The java directory has a package 'util' with three classes.  
  
### PrintFormatting  
This class exposes one method with the signature  `public static void print(Object... objects)`.  
Each object is printed on a separate line by calling `String.valueOf(o)`.  
Arrays and Collections are printed by first printing an opening square bracket '['.  
Then each element in the array is printed on a separate line and is indented.  
On a separate line a closing square bracket `]` is printed.  
Elements in nested arrays are indented multiple times.  
Finally, it makes typing quicker as there is no need to write the full `System.out.println`.  
  
### ProgressBar  
This class exposes two methods.  
```java
public static String formatBar(int workDone, int workTotal)
public static String formatBar(int workDone, int workTotal, boolean withCarriageReturn)
```  
They can be used to create a progress bar that looks like this `|-----------------   | 85% (a/b)`.  
Dashes form the bar, a is the work done, b is the total work to be done.  
The percentage progress is calculated as `progress = workDone*100.0/workTotal`.  
The returned String starts with `\r`, so that consecutive printing can replace the previous progress bar.  
```java
for (int i = 0; i < SUPER_LONG_LOOP; i++) {
    timeConsumingOperation();
    String bar = formatBar(int workDone, int workTotal);
    System.out.print(bar);
}
```  
Calling the function with `false` as the third argument constructs the progress bar without the initial carriage return.  
Note that putting the carriage return at the end will cause the progress bar to disappear immediately after appearing.
  
### Logger  
This class lets the user log information to a file.  
It requires access to the PrintFormatting the class for the purposes of reporting file IO errors and constant definition.  
Logger objects are created by `Logger logger = new Logger(String filename)`.  
Subsequent calls to `logger.log(String line)` will write the line to the previously opened file.  
A new line `\r\n` is attached to the argument.  
The method `logger.log(Exception e)` logs the exception to the file via calls to the log function above.  
At end of execution, call `logger.close()` to close the logging file.    
When a log file is opened, a new [Thread](https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html "Oracle's Documentation on Java Thread")
 is started that periodically flushes the contents of the log.  
This function checks if the contents have been updated between activation periods.  
The period between flushes is 5 seconds (5000ms).  
Calling `Logger.close()` tells the thread to stop, which will happen at the end of the next period.  
  
### Props  
This class enables reading of properties files.  
Use `Props.load(String filename)` to load properties from a file called "filename". This can be absolute or relative path.  
Use `Props.load()` to load properties from the default filename "default.props".  
The Props class uses a HashMap to store all the loaded properties.  
Loads are cumulative. That is to say, loading multiple files will cause the Props class to store all properties.  
Furthermore, if multiple loaded properties files contain the same key, only the latest value will be kept.  
Use `Props.clear()` to remove all key-value entries.  
The class provides several `get` methods to retrieve the value associated with a property.
and a `Props.size()` method to retrieve the number of properties currently loaded.  
 