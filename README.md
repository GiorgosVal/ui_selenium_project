# Demo UI test project with Selenium/TestNG
## System requirements
For the project to build and run you must have the following installed:
- Java 11
- Maven 3.*
- Allure 2.*

## How to run the tests
### With the default properties
To build and run the tests using the default properties of the `testng.xml` just run the command:
```shell
mvn clean test
```
### Override the default properties
To override the default properties of the `testng.xml` without any code change, you can pass the
property you want using the syntax `-Dproperty_name=property_value`. Available options are:
- `browser`: one of `chrome`, `firefox` (case-insensitive)
- `timeout` : integer
- `headless`: `true` or `false`

Example:
```shell
mvn clean test -Dbrowser=firefox -Dheadless=true -Dtimeout=10
```

## Check results
### In Maven
Console will print the outcome of the tests, for example:
```shell
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 59.201 s - in TestSuite
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```
### In TestNG
Run the command
```shell
open target/surefire-reports/index.html
```
### In Allure
Run the command
```shell
allure serve target/allure-results
```