package etraveli.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class firstPage {
    Page page;

    public firstPage() {
        // Constructor vacío
    }

    public firstPage(Page page) {
        this.page = page;

    }

    public void firstSearch() {
        // Ir a la pagina principal
        page.navigate("https://www.flightnetwork.com/");
        if (page.locator("#onetrust-accept-btn-handler").isVisible()) {
            page.locator("#onetrust-accept-btn-handler").click();
        }

        page.locator("[data-testid='searchForm-singleBound-origin-input']").click();
        page.locator("input#searchForm-singleBound-origin-input").fill("Buenos Aires");
        Locator origin = page.locator("div >> text=Buenos Aires (todos los aeropuertos), Argentina");
        origin.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        origin.click();

        page.locator("[data-testid='searchForm-singleBound-destination-input']").click();
        page.keyboard().type("RIO DE JANEIRO");
        Locator dest = page.locator("div >> text=Rio De Janeiro (Galeao), Brasil");
        dest.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        dest.click();

        page.locator("[data-testid='singleBound.departureDate-input']").fill("2025-05-06");
        page.locator("[data-testid='singleBound.returnDate-input']").fill("2025-05-30");
        page.locator("[data-testid='searchForm-passengers-dropdown']").click();
        page.locator("text='1'").click();
        page.locator("[data-testid='searchForm-cabinClasses-dropdown']").click();
        page.locator("#headlessui-listbox-option-4").click();

        page.locator("[data-testid='searchForm-searchFlights-button']").click();
        page.waitForSelector("[data-testid='resultPage-filtersContainer']");
    }
}
