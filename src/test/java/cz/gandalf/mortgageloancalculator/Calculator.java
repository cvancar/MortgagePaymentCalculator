/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.gandalf.mortgageloancalculator;

/**
 *
 * @author pcvancar
 */
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class Calculator {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    private final String MONTHLY_PRINCIPAL_INTEREST = "$1,073.64";
    private final String LOAN_TO_VALUE_RATIO = "85.11%";
    private final String TOTAL_MONTHLY_PAYMENT = "$1,482.39";

    @BeforeSuite(alwaysRun = true)
    public void setUpSuite() throws Exception {
        baseUrl = "https://www.mortgageloan.com/calculator";
    }

    @Test(groups = {"sanity"})
    public void testWebAppAvailable() {
        try {
            WebClient client = new WebClient();
            int httpResponse = client.getPage(baseUrl).getWebResponse().getStatusCode();
            System.out.println("testWebAppAvailable - Web App is available. HTTP response code is " + httpResponse);
            Assert.assertTrue(httpResponse == 200, "Web App is available. HTTP response code is " + httpResponse);
        } catch (IOException ioe) {
            System.out.println("testWebAppAvailable - Web App seems to be unavailable " + ioe.getMessage());
            Assert.assertTrue(ioe.getMessage().isEmpty(), "Web App seems to be unavailable ");
        } catch (ScriptException se) {
            System.out.println("testWebAppAvailable - Script exeption is passed " + se.getMessage());
        }
    }

    @BeforeGroups("functional")
    public void setUpGroup() throws Exception {
        System.setProperty("webdriver.gecko.driver", "D:\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
    }

    @Test(dependsOnGroups = {"sanity"}, groups = {"functional"})
    public void testMortgagePaymentCalculator() throws Exception {

        String monthlyPrincipalInterest = "";
        String loadToValueRatio = "";
        String totalMonthlyPayments = "";

        driver.get(baseUrl);
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Get this Widget'])[1]/following::span[1]")).click();
        driver.findElement(By.id("calculator_widget_amount")).click();
        driver.findElement(By.id("calculator_widget_amount")).clear();
        driver.findElement(By.id("calculator_widget_amount")).sendKeys("200000");
        driver.findElement(By.id("calculator_widget_HomeValue")).click();
        driver.findElement(By.id("calculator_widget_HomeValue")).clear();
        driver.findElement(By.id("calculator_widget_HomeValue")).sendKeys("235000");
        driver.findElement(By.id("calculator_widget_interest")).click();
        driver.findElement(By.id("calculator_widget_interest")).clear();
        driver.findElement(By.id("calculator_widget_interest")).sendKeys("5");
        driver.findElement(By.id("calculator_widget_Length")).click();
        driver.findElement(By.id("calculator_widget_Length")).clear();
        driver.findElement(By.id("calculator_widget_Length")).sendKeys("30");
        driver.findElement(By.linkText("Next")).click();
        
        driver.findElement(By.id("calculator_widget_PropertyTaxes")).click();
        driver.findElement(By.id("calculator_widget_PropertyTaxes")).clear();
        driver.findElement(By.id("calculator_widget_PropertyTaxes")).sendKeys("2000");
        driver.findElement(By.id("calculator_widget_Insurance")).click();
        driver.findElement(By.id("calculator_widget_Insurance")).clear();
        driver.findElement(By.id("calculator_widget_Insurance")).sendKeys("1,865");
        driver.findElement(By.id("calculator_widget_PMI")).click();
        driver.findElement(By.id("calculator_widget_PMI")).clear();
        driver.findElement(By.id("calculator_widget_PMI")).sendKeys("0.52");
        driver.findElement(By.id("calculator-form-wizard")).click();
        driver.findElement(By.linkText("Show Results")).click();

        monthlyPrincipalInterest = driver.findElement(By.xpath("//th[contains(text(),'Monthly Principal & Interests')]/../td")).getText();
        loadToValueRatio = driver.findElement(By.xpath("//th[contains(text(),'Loan To Value Ratio')]/../td")).getText();
        totalMonthlyPayments = driver.findElement(By.xpath("//th[contains(text(),'Total Monthly Payments')]/../td")).getText();

        System.out.println("testMortgagePaymentCalculator - monthlyPrincipalInterest: " + monthlyPrincipalInterest);
        System.out.println("testMortgagePaymentCalculator - loadToValueRatio: " + loadToValueRatio);
        System.out.println("testMortgagePaymentCalculator - totalMonthlyPayments: " + totalMonthlyPayments);
        
        Assert.assertTrue(monthlyPrincipalInterest.equalsIgnoreCase(MONTHLY_PRINCIPAL_INTEREST)
                && loadToValueRatio.equalsIgnoreCase(LOAN_TO_VALUE_RATIO)
                && totalMonthlyPayments.equalsIgnoreCase(TOTAL_MONTHLY_PAYMENT),
                "Mortgage payments were not calculated properly.");
    }

    @AfterGroups("functional")
    public void cleanUpGroup() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
