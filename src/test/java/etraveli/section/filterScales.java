package etraveli.section;

import java.util.List;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class filterScales {
    Page page;

    public filterScales() {
        // Constructor vacío
    }

    public filterScales(Page page) {
        this.page = page;

    }

    public void MaxOneScale() {
        page.waitForSelector("[data-testid='resultPage-filtersContainer']");
        page.locator("[data-testid='MAX_STOPS-max']").click();
        page.waitForTimeout(9000);
        List<Locator> vuelos = page.locator("._1tsyql1a._1tsyql1c.css-1q857kt").all();
        // System.out.println("Cantidad de vuelos: " + vuelos.size());

        for (Locator vuelo : vuelos) {
            List<ElementHandle> escalas = page.locator("[data-testid='trip-bound-1-info-stops']").elementHandles();
            for (ElementHandle escala : escalas) {
                String texto = escala.innerText().trim();
                // System.out.println("comienzo a validar: " + texto);
                if (!(texto.equals("Vuelo directo") || texto.equals("1 Cambio"))) {
                    System.out.println("Invalid stopover found " + texto);
                    throw new RuntimeException("Failed test: an option with more than one stopover was found " + texto);
                }
            }
        }
        System.out.println("Test completed: all options have 1 or 0 stopovers.");
    }

    public void DirectFight() {
        page.waitForSelector("[data-testid='resultPage-filtersContainer']");
        page.locator("[data-testid='MAX_STOPS-direct']").click();
        page.waitForTimeout(9000);
        List<Locator> vuelos = page.locator("._1tsyql1a._1tsyql1c.css-1q857kt").all();
        // System.out.println("Cantidad de vuelos: " + vuelos.size());

        for (Locator vuelo : vuelos) {
            List<ElementHandle> escalas = page.locator("[data-testid='trip-bound-1-info-stops']").elementHandles();
            // System.out.println("Cantidad de escalas: " + escalas.size());
            for (ElementHandle escala : escalas) {
                String texto = escala.innerText().trim();
                // System.out.println("comienzo a validar: " + texto);
                if (!(texto.equals("Vuelo directo"))) {
                    throw new RuntimeException(
                            "Failed test: an option that is not a direct flight was found " + texto);
                }
            }
        }
        System.out.println("Test completed: all options are direct flights.");
    }

    public void AllFight() {
        page.locator("[data-testid='MAX_STOPS-all']").click();
        page.waitForTimeout(9000);

        String text = page.locator("[data-testid='resultPage-filtersContainer']").textContent().trim();

        String[] partes = text.split(" ");
        int mostrados = Integer.parseInt(partes[1]);
        int total = Integer.parseInt(partes[3]);

        if (mostrados != total) {
            throw new RuntimeException(
                    "Failed test: the displayed flights (" + mostrados + ") do not match the total (" + total
                            + ").");
        }

        System.out.println("Test completed successfully: All flights are visible. Displayed: " + mostrados
                + ", Total: " + total);
    }

    public void resetFilter() {
        page.locator("[data-testid='MAX_STOPS-direct']").click();
        page.waitForTimeout(9000);
        String text = page.locator("[data-testid='resultPage-filtersContainer']").textContent().trim();

        String[] partes = text.split(" ");
        int mostrados = Integer.parseInt(partes[1]);
        int total = Integer.parseInt(partes[3]);

        if (mostrados == total) {
            throw new RuntimeException(
                    "Failed test: No flights were filtered for direct flights or all flights are direct.");
        }
        page.locator("[data-testid='resultPage-filterHeader-MAX_STOPSFilterResetButton-button']").click();
        boolean isChecked = page.locator("[data-testid='MAX_STOPS-all']").isChecked();
        if (!isChecked) {
            System.out.println("The 'MAX_STOPS-all' filter is not selected.");
            throw new RuntimeException("Failed test: the 'MAX_STOPS-all' filter is not selected.");
        }
        boolean isVisible = page.locator("[data-testid='resultPage-filterHeader-MAX_STOPSFilterResetButton-button']")
                .isVisible();

        if (isVisible) {
            System.out.println("The filter reset button is still visible.");
            throw new RuntimeException("Failed test: the filter reset button is still visible.");
        }

        System.out.println("Test completed successfully: The filter has been reset correctly.");

    }

    public void SelfTransferFight() {
        page.locator("#self-transfer-only").click();
        page.waitForTimeout(9000);
        List<Locator> vuelos = page.locator("._1tsyql1a._1tsyql1c.css-1q857kt").all();
        // System.out.println("Cantidad de vuelos: " + vuelos.size());

        for (Locator vuelo : vuelos) {
            List<ElementHandle> escalas = page.locator("[data-testid='trip-bound-1-info-stops']").elementHandles();
            // System.out.println("Cantidad de escalas: " + escalas.size());
            for (ElementHandle escala : escalas) {
                String texto = escala.innerText().trim();
                // System.out.println("comienzo a validar: " + texto);
                if (!(texto.equals("Vuelo directo"))) {
                    throw new RuntimeException(
                            "Test SelfTransferFilter() failed: an option that is not a direct flight was found "
                                    + texto);
                }
            }
        }
        System.out.println("Test completed: all options are direct flights.");
    }

}