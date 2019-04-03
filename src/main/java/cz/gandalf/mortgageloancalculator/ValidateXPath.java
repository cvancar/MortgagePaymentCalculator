/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.gandalf.mortgageloancalculator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author pcvancar
 */
public class ValidateXPath {
    
    public static void main(String[] arguments){
        String baseUrl="file:///D:/New1.html";
        System.setProperty("webdriver.gecko.driver", "D:\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get(baseUrl);
        String monthlyPrincipalInterest = driver.findElement(By.xpath("//th[contains(text(),'Monthly Principal & Interests')]/../td")).getText();
        String loadToValueRatio = driver.findElement(By.xpath("//th[contains(text(),'Loan To Value Ratio')]/../td")).getText();
        String totalMonthlyPayments = driver.findElement(By.xpath("//th[contains(text(),'Total Monthly Payments')]/../td")).getText();
        System.out.println("\n\n\n************************");
        System.out.println("monthlyPrincipalInterest: " + monthlyPrincipalInterest);
        System.out.println("loadToValueRatio: " + loadToValueRatio);
        System.out.println("totalMonthlyPayments: " + totalMonthlyPayments);
        System.out.println("************************ \n\n\n");
        driver.quit();
    }
    
}
