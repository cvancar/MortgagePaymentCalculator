# MortgagePaymentCalculator

Before executing this test suite you need to download and unzip Firefox Selenium webdriver https://github.com/mozilla/geckodriver/

Than set Java system property webdriver.gecko.driver in the code System.setProperty("webdriver.gecko.driver", "D:\geckodriver.exe");

or in the command line -Dwebdriver.gecko.driver="D:\geckodriver.exe"

Execute test from project root mvn test -Dwebdriver.gecko.driver="D:\geckodriver.exe" -Dtest=Calculator
