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
        throw new RuntimeException("Test fallido: se encontró un vuelo de 'Aerolineas Argentinas'");
      }
    }

    System.out
        .println("Test completado correctamente: todos los vuelos verificados, ninguno es de Aerolíneas Argentinas.");

  }

  public void filterByAirline() {
    page.locator("[data-testid='resultPage-AIRLINESFilter-deselect-all-button']").click();
    page.locator("#airlines-AR").click();
    List<Locator> logos = page.locator("[data-testid='tripDetails-carrier-logo'] img").all();

    for (Locator logo : logos) {
      String alt = logo.getAttribute("alt");
      if (!"Aerolineas Argentinas".equals(alt)) {
        throw new RuntimeException("Test fallido: Se encontro  un vuelo diferente a Aerolíneas Argentinas");
      }
    }

    System.out
        .println("Test completado correctamente: todos los vuelos verificados, todos son de Aerolíneas Argentinas.");

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
      throw new RuntimeException("No se encontraron nuevas aerolíneas al hacer clic en 'Mostrar más'.");
    }
    System.out
        .println("Test completado correctamente: Se encontraron nuevas aerolíneas al hacer clic en 'Mostrar más'.");
  }
}