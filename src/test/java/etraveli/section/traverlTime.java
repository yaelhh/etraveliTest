package etraveli.section;

import java.util.List;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;

public class traverlTime {
    Page page;

    public traverlTime() {
        // Constructor vacío
    }

    public traverlTime(Page page) {
        this.page = page;

    }

    public void ChangeTime() {
        Locator header = page.locator("[data-testid='resultPage-TRAVEL_TIME-header']");
        header.scrollIntoViewIfNeeded();
        Locator slider = page.locator("[data-testid='resultPage-TRAVEL_TIMEFilter-content'] input[type='range']");
        // Solucion obtenida de chatGPT
        // Obtener posición del slider
        BoundingBox box = slider.boundingBox();

        // Calcular nueva posición: mover el handle hacia la izquierda
        double newX = box.x + box.width * 0.08; // por ejemplo, moverlo al 20%
        double centerY = box.y + box.height / 2;

        // Simular arrastre con mouse
        page.mouse().move(box.x + box.width - 1, centerY); // ir al extremo derecho
        page.mouse().down();
        page.mouse().move(newX, centerY);
        page.mouse().up();

        page.waitForTimeout(2000);

        String valueText = slider.getAttribute("aria-valuetext");
        int valueHour = Integer.parseInt(valueText.replaceAll("\\D", "").trim());

        List<Locator> timeFlighLocators = page
                .locator("p[data-testid='trip-bound-1-info-duration'], p[data-testid='trip-bound-2-info-duration']")
                .all();
        for (Locator flight : timeFlighLocators) {
            String[] timeText = flight.textContent().split(" ");
            String hoursText = timeText[0].trim();
            String minutesText = timeText[1].trim();

            int hours = Integer.parseInt(hoursText.replaceAll("\\D", "").trim());
            int minutes = Integer.parseInt(minutesText.replaceAll("\\D", "").trim());
            if (hours > valueHour || (hours == valueHour && minutes > 0)) {
                throw new RuntimeException(
                        "Test fallido: Se encontró un vuelo con tiempo de viaje mayor a " + valueHour + " horas  ");
            }

        }
        System.out.println(
                "Test completado correctamente: Todos los vuelos tienen un tiempo de viaje menor a " + valueHour
                        + " horas.");
    }

}