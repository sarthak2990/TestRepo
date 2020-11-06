# WebTest

Hi ,

Thanks for providing the oppurnity to attempt this test, it is interesting one.

So I have created this framework using TestNg + Java + Selenium + Maven

And the commands to run the test is 

```mvn clean test```  or      
```mvn clean test -Dbrowser=chrome/firefox```
 
if you want to provide browser from command line
or 

```mvn clean test -Dbrowser=chrome -DthreadCount=2``` 

if you want to run test in parallel and set the number of threads from commandline only

I have integrated Allure report with this framework so once tests are run we need to execute another command to generate allure report
 
 ```mvn io.qameta.allure:allure-maven:report```

The path for the allure report is ```/target/site/allure-maven-plugin/index.html```

Apart from report I have integrated Test fail Retry listner which will rerun our failed test 2 times.

Also, Locators are stored in CheckoutPage.properties and for storing locatores for xpath I have added XP_ as prefix and ClassName CS_, NM_ for name  
so to handle this we have Created ElementLocator.java class to handle locator, so when in future if your test fails due to change in locator you just need to update your property file and no need to do any change in java code

Feel free to connect with me at sarthak2990@gmail.com incase of any questions or doubt
