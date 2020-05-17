# CarnivalTest Framework

INSTALLATION INSTRUCTIONS:
1. git clone https://github.com/juanmontesd/CarnivalTest.git
2. mvn clean install -DskipTests
3. mvn eclipse:eclipse or mvn idea:idea
4. open proyect with you IDE

HOW TO RUN:
1. Fist have to update the drivers for your specific OS in the file 
[Driver Class](src/main/java/com/automation/web/driver/Driver.java)
(in the *src/main/resources* folder you can fine some drivers):
    - In the line 41 change the path of the driver for the firefox browser
    - In the line 45 change the path of the driver for the firefox browser
2. Execute one of the xml suits that are in the folder *src/test/resources*
    - Run in firefox [Suite Firefox](src/test/resources/suiteTestsFirefox.xml)
    - Run in chrome [Suite Chrome](src/test/resources/suiteTestsChrome.xml)

