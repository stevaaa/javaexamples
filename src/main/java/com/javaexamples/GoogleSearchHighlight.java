package com.javaexamples;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import java.util.concurrent.TimeUnit;

public class GoogleSearchHighlight {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/chromedriver");

        // Set Chrome options to disable extensions and notifications
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");

        // Create a new instance of ChromeDriver with the specified options
        WebDriver driver = new ChromeDriver(options);

        // Configure implicit wait time
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Open Google homepage
        driver.get("https://www.google.com");

        // Find the search input element
        WebElement searchInput = driver.findElement(By.name("q"));

        // Highlight the search input element with a red border
        highlightElement(driver, searchInput);

        // Enter the search keyword
        searchInput.sendKeys("your search keyword");

        // Find the search button element
        WebElement searchButton = driver.findElement(By.name("btnK"));

        // Highlight the search button element with a red border
        highlightElement(driver, searchButton);

        // Click on the search button
        searchButton.click();

        // Find the first search result link
        WebElement firstSearchResult = driver.findElement(By.cssSelector("div#rso a"));

        // Highlight the first search result link with a red border
        highlightElement(driver, firstSearchResult);

        // Click on the first search result link
        firstSearchResult.click();

        // Wait for a few seconds to observe the highlighted elements
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the browser
        driver.quit();
    }

    // Method to highlight an element with a red border
    private static void highlightElement(WebDriver driver, WebElement element) {
        // Create an instance of the Actions class
        Actions actions = new Actions(driver);

        // Execute JavaScript to add a red border to the element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='2px solid red'", element);

        // Move the mouse to the element to trigger the highlight effect
        actions.moveToElement(element).perform();
    }
}
