package etraveli.section;

import java.util.List;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class filterAirline {
  Page page;

  public filterAirline() {
    // Constructor vacío
  }

  public filterAirline(Page page) {
    this.page = page;

  }

  public void filterWithoutThisAirline() {
    page.locator("#airlines-AR").click();
    List<Locator> logos = page.locator("[data-testid='tripDetails-carrier-logo'] img").all();

    for (Locator logo : logos) {
      String alt = logo.getAttribute("alt");
      if ("Aerolineas Argentinas".equals(alt)) {
        throw new RuntimeException("Failed test: a flight from 'Aerolineas Argentinas' was found.");
      }
    }

    System.out
        .println("Test completed successfully: all flights were verified, and none are from Aerolíneas Argentinas.");

  }

  public void filterByAirline() {
    page.locator("[data-testid='resultPage-AIRLINESFilter-deselect-all-button']").click();
    page.locator("#airlines-AR").click();
    List<Locator> logos = page.locator("[data-testid='tripDetails-carrier-logo'] img").all();

    for (Locator logo : logos) {
      String alt = logo.getAttribute("alt");
      if (!"Aerolineas Argentinas".equals(alt)) {
        throw new RuntimeException("Failed test: a flight different from 'Aerolineas Argentinas' was found.");
      }
    }

    System.out
        .println("Test completed successfully: all flights were verified, and all are from Aerolíneas Argentinas.");

  }

  public void filterByAirlineShowMore() {
    List<Locator> list = page
        .locator("[data-testid='resultPage-AIRLINESFilter-content'] ._15h2k5o0.ioeri02._15h2k5o6._15h2k5o1").all();
    int AirlinesList = list.size();
    // System.out.println("Cantidad de aerolíneas: " + AirlinesList);
    page.locator("[data-testid='resultPage-AIRLINESFilter-show-more-button']").click();
    List<Locator> newList = page
        .locator("[data-testid='resultPage-AIRLINESFilter-content'] ._15h2k5o0.ioeri02._15h2k5o6._15h2k5o1").all();
    int AirlinesNewList = newList.size();
    // System.out.println("Cantidad de aerolíneas después de hacer clic en 'Mostrar
    // más': " + AirlinesNewList);

    if (AirlinesList == AirlinesNewList) {
      throw new RuntimeException("No new airlines were found after clicking 'Show more'.");
    }
    System.out
        .println("Test completed successfully: new airlines were found after clicking 'Show more'.");
  }
}