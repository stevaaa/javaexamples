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
import java.util.Scanner;

public class LocatorAnalyzer1 {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/chromedriver");

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Provide the website address
        String websiteAddress = "http://www.google.com";

        // Open the website in the browser
        driver.get(websiteAddress);

        // Get the HTML source code of the webpage
        String html = driver.getPageSource();

        // Parse the HTML using Jsoup
        Document document = Jsoup.parse(html);

        // Find all elements in the DOM
        Elements elements = document.body().select("*");

        // Analyze each element and suggest the best locator
        for (Element element : elements) {
            suggestLocator(driver, element);
        }

        // Get user input for the element to find
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the tag name of the element to find: ");
        String tagName = scanner.nextLine();

        // Find the element using the suggested locator
        String suggestedLocator = suggestLocator(driver, tagName);
        if (suggestedLocator != null) {
            WebElement element = driver.findElement(By.xpath(suggestedLocator));
            System.out.println("Element found using locator: " + suggestedLocator);
            System.out.println("Element Text: " + element.getText());
        } else {
            System.out.println("Element not found or unsupported tag name.");
        }

        // Close the browser
        driver.quit();
    }

    // Suggest the best locator for an element
    private static void suggestLocator(WebDriver driver, Element element) {
        // Check if the element has an ID attribute
        String id = element.attr("id");
        if (!id.isEmpty()) {
            System.out.println("Element: " + element.tagName());
            System.out.println("Locator: By.id(\"" + id + "\")");
            System.out.println("------------------------");
            return;
        }

        // Check if the element has a unique class name
        String className = element.attr("class");
        if (!className.isEmpty()) {
            if (className.contains(" ")) {
                System.out.println("Element: " + element.tagName());
                System.out.println("Locator: By.cssSelector(\"." + className.replaceAll(" ", ".") + "\")");
                System.out.println("------------------------");
            } else {
                System.out.println("Element: " + element.tagName());
                System.out.println("Locator: By.className(\"" + className + "\")");
                System.out.println("------------------------");
            }
            return;
        }

        // Check if the element has a name attribute
        String name = element.attr("name");
        if (!name.isEmpty()) {
            System.out.println("Element: " + element.tagName());
            System.out.println("Locator: By.name(\"" + name + "\")");
            System.out.println("------------------------");
            return;
        }

        // Check if the element has a link text
        String linkText = element.text();
        if (!linkText.isEmpty()) {
            System.out.println("Element: " + element.tagName());
            System.out.println("Locator: By.linkText(\"" + linkText + "\")");
            System.out.println("------------------------");
            return;
        }

        // Check if the element has a partial link text
        String partialLinkText = element.text();
        if (!partialLinkText.isEmpty()) {
            System.out.println("Element: " + element.tagName());
            System.out.println("Locator: By.partialLinkText(\"" + partialLinkText + "\")");
            System.out.println("------------------------");
            return;
        }

        // Check if the element has a CSS selector
        String cssSelector = element.cssSelector();
        if (!cssSelector.isEmpty()) {
            System.out.println("Element: " + element.tagName());
            System.out.println("Locator: By.cssSelector(\"" + cssSelector + "\")");
            System.out.println("------------------------");
            return;
        }

        // Check if the element has an XPath
        String xPath = getXPath(driver, element);
        if (!xPath.isEmpty()) {
            System.out.println("Element: " + element.tagName());
            System.out.println("Locator: By.xpath(\"" + xPath + "\")");
            System.out.println("------------------------");
            return;
        }

        // If no suitable locator is found, print null
        System.out.println("Element: " + element.tagName());
        System.out.println("Locator: null");
        System.out.println("------------------------");
    }

    // Get the XPath of an element
    private static String getXPath(WebDriver driver, Element element) {
        WebElement webElement = driver.findElement(By.xpath("//body"));
        return webElement.equals(element) ? "body" : getXPath(driver, element.parent()) + "/" + element.tagName();
    }

    // Suggest the best locator for a specific tag name
    private static String suggestLocator(WebDriver driver, String tagName) {
        WebElement element = driver.findElement(By.tagName(tagName));

        // Check if the element has an ID attribute
        String id = element.getAttribute("id");
        if (!id.isEmpty()) {
            return "//*[@id='" + id + "']";
        }

        // Check if the element has a unique class name
        String className = element.getAttribute("class");
        if (!className.isEmpty()) {
            if (className.contains(" ")) {
                return "//*[contains(@class, '" + className + "')]";
            } else {
                return "//*[@class='" + className + "']";
            }
        }

        // Check if the element has a name attribute
        String name = element.getAttribute("name");
        if (!name.isEmpty()) {
            return "//*[@name='" + name + "']";
        }

        // Return a relative XPath by default
        return "./" + tagName;
    }
}
