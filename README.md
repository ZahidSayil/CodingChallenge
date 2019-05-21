# CodingChallenge
Parsing CSV file and Inserting Data into Database (Using SQLITE &amp; JDBC)
Challenge : 
Customer X just informed us that we need to churn out a code enhancement ASAP for a new project.  Here is what they need:

1. We need a Java application that will consume a CSV file, parse the data and insert to a SQLite In-Memory database.  
a. Table X has 10 columns A, B, C, D, E, F, G, H, I, J which correspond with the CSV file column header names.
b. Include all DDL in submitted repository
c. Create your own SQLite DB

2. The data sets can be extremely large so be sure the processing is optimized with efficiency in mind.  

3. Each record needs to be verified to contain the right number of data elements to match the columns.  
a. Records that do not match the column count must be written to the bad-data-<timestamp>.csv file
b. Elements with commas will be double quoted

4. At the end of the process write statistics to a log file
a. # of records received
b. # of records successful
c. # of records failed

My Approach: 
The whole assignment is divided into three functionalities. 
1. I read the data from the CSV file with the help of BufferedReading , parsing it using colomn by column. 
   Storing the data in a collection ( Array ). I have used a List of objects to store indivial index of array for each
   object. 
2. Loading the data into database , using JDBC API I interact with the database ( SQLITE) insert the data into the corresponding 
  columns. 
3. A simple method having a data writer , which write the statistics to a log file. 

Location of files: 
I am assuming the database and log file path is in the current directory. 

JAR File directon : 
I have used only one JAR file which can be download from
https://bitbucket.org/xerial/sqlite-jdbc/downloads/. 
Kindly use the 3.27.2.1 version. 

Difficulites Faced: 
Due to the time shortage, couldn't work on the better optimize solution. If you know the optimized solution, I am willing to learn it.
