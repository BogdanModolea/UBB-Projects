package org.example.features.search;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.WebElementFacade;
import java.util.stream.Collectors;

import net.serenitybdd.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;

import java.util.List;

@DefaultUrl("http://www.cs.ubbcluj.ro/")
public class UbbClujPage extends PageObject {

    @FindBy(name="s")
    private WebElementFacade searchInput;

    @FindBy(className="lang-item-en")
    private WebElementFacade changeLanguageElement;

    public void enterKeywordsAndSearch(String keyword) {
        searchInput.typeAndEnter(keyword);
    }

    public void changeLanguage() {
        changeLanguageElement.click();
    }

    public List<String> getFirstTitleAfterSearch() {
        WebElementFacade titles = find(By.className("title"));
        return titles.findElements(By.tagName("a")).stream()
                .map( element -> element.getAttribute("title"))
                .collect(Collectors.toList());
    }

    public String getTitlesAfterLanguageChange() {
        return find(By.cssSelector("h2.title")).getText();
    }
}