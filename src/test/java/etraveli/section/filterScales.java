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
                    System.out.println("Escala inválida encontrada: " + texto);
                    throw new RuntimeException("Test fallido: se encontró una opción con más de una escala → " + texto);
                }
            }
        }
        System.out.println("Test completado: todas las opciones tienen 1 o 0 escalas.");
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
                            "Test fallido: se encontró una opción que no es un vuelo directo → " + texto);
                }
            }
        }
        System.out.println("Test completado: todas las opciones son vuelos directos.");
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
                    "Test fallido: Los vuelos mostrados (" + mostrados + ") no coinciden con el total (" + total
                            + ").");
        }

        System.out.println("Test completado correctamente: Todos los vuelos son visibles. Mostrados: " + mostrados
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
                    "Test fallido: No se filtro por los vuelos directos o todos los vuelos son directos.");
        }
        page.locator("[data-testid='resultPage-filterHeader-MAX_STOPSFilterResetButton-button']").click();
        boolean isChecked = page.locator("[data-testid='MAX_STOPS-all']").isChecked();
        if (!isChecked) {
            System.out.println("El filtro 'MAX_STOPS-all' no está seleccionado.");
            throw new RuntimeException("Test fallido: el filtro 'MAX_STOPS-all' no está seleccionado.");
        }
        boolean isVisible = page.locator("[data-testid='resultPage-filterHeader-MAX_STOPSFilterResetButton-button']")
                .isVisible();

        if (isVisible) {
            System.out.println("El botón de restablecer filtro sigue visible.");
            throw new RuntimeException("Test fallido: el botón de restablecer filtro sigue visible.");
        }

        System.out.println("Test completado correctamente: El filtro ha sido restablecido correctamente.");

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
                            "Test SelfTransferFilter() fallido: se encontró una opción que no es un vuelo directo "
                                    + texto);
                }
            }
        }
        System.out.println("Test completado: todas las opciones son vuelos directos.");
    }

}