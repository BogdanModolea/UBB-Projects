package org.example.features.search;

import org.example.pages.DictionaryPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class EndUserStepsUbbCluj {

    UbbClujPage ubbClujPage;

    @Step
    public void entersAndSearches(String keyword) {
        ubbClujPage.enterKeywordsAndSearch(keyword);
    }

    @Step
    public void changeLanguage() {
        ubbClujPage.changeLanguage();
    }

    @Step
    public void shouldSeeFirstTitleAfterSearch(String definition) {
        assertThat(ubbClujPage.getFirstTitleAfterSearch(), hasItem(containsString(definition)));
    }

    @Step
    public void shouldSeeMainPageTitle(String definition) {
        assertThat(ubbClujPage.getTitlesAfterLanguageChange(), containsString(definition));
    }

    @Step
    public void is_the_home_page() {
        ubbClujPage.open();
    }

    @Step
    public void looks_for(String term) {
        entersAndSearches(term);
    }
}