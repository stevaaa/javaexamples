package com.javaexamples;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;

public class LocatorAnalyzer {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/chromedriver");

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Provide the website address
        String websiteAddress = "https://automationintesting.online/";

        // Open the website in the browser
        driver.get(websiteAddress);

        // Get the HTML source code of the webpage
        String html = driver.getPageSource();

        // Parse the HTML using Jsoup
        Document document = Jsoup.parse(html);

        // Create a map to store the suggested locators for each element
        Map<String, String> locators = new HashMap<>();

        // Find all elements in the DOM
        Elements elements = document.body().select("*");

        // Analyze each element and suggest the best locator
        for (Element element : elements) {
            String tagName = element.tagName();
            String suggestedLocator = suggestLocator(driver, element);
            locators.put(tagName, suggestedLocator);
        }

        // Print the suggested locators
        for (Map.Entry<String, String> entry : locators.entrySet()) {
            System.out.println("Element: " + entry.getKey());
            System.out.println("Locator: " + entry.getValue());
            System.out.println("------------------------");
        }

        // Close the browser
        driver.quit();
    }

    // Suggest the best locator for an element
    private static String suggestLocator(WebDriver driver, Element element) {
        // Check if the element has an ID attribute
        String id = element.attr("id");
        if (!id.isEmpty()) {
            return "By.id(\"" + id + "\")";
        }

        // Check if the element has a unique class name
        String className = element.attr("class");
        if (!className.isEmpty()) {
            if (className.contains(" ")) {
                return "By.cssSelector(\"." + className.replaceAll(" ", ".") + "\")";
            } else {
                return "By.className(\"" + className + "\")";
            }
        }

        // Check if the element has a name attribute
        String name = element.attr("name");
        if (!name.isEmpty()) {
            return "By.name(\"" + name + "\")";
        }

        // Check if the element has a link text
        String linkText = element.text();
        if (!linkText.isEmpty()) {
            return "By.linkText(\"" + linkText + "\")";
        }

        // Check if the element has a partial link text
        String partialLinkText = element.text();
        if (!partialLinkText.isEmpty()) {
            return "By.partialLinkText(\"" + partialLinkText + "\")";
        }

        // Check if the element has a CSS selector
        String cssSelector = element.cssSelector();
        if (!cssSelector.isEmpty()) {
            return "By.cssSelector(\"" + cssSelector + "\")";
        }

        // Check if the element has an XPath
        String xPath = getXPath(driver, element);
        if (!xPath.isEmpty()) {
            return "By.xpath(\"" + xPath + "\")";
        }

        // If no suitable locator is found, return null
        return null;
    }

    // Get the XPath of an element
// Get the XPath of an element
    private static String getXPath(WebDriver driver, Element element) {
        WebElement webElement = driver.findElement(By.xpath("//body"));
        return webElement.equals(element) ? "body" : getXPath(driver, element.parent()) + "/" + element.tagName();
    }

}