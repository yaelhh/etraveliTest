package etraveli.section;

import java.util.List;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.WaitForSelectorState;

public class timeSection {
    Page page;

    public timeSection() {
        // Constructor vacío
    }

    public timeSection(Page page) {
        this.page = page;

    }

    public void changeDepartureSection() {
        Locator salida = page.locator("[data-testid='resultPage-departureArrivalFilter-departure1-radio']");
        salida.scrollIntoViewIfNeeded();

        Locator sliderContainer = page.locator("[data-testid='resultPage-departureArrivalFilter-departure1-slider']");
        sliderContainer.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Locator allHandles = sliderContainer.locator("input");

        Locator handle = allHandles.nth(1);

        // Esto fue obtenido de ChatGPT
        BoundingBox box = handle.boundingBox();

        // Calcular nueva posición: mover el handle hacia la izquierda
        double newX = box.x + box.width * 0.70; // por ejemplo, moverlo al 20%
        double centerY = box.y + box.height / 2;

        // Simular arrastre con mouse
        page.mouse().move(box.x + box.width - 1, centerY); // ir al extremo derecho
        page.mouse().down();
        page.mouse().move(newX, centerY);
        page.mouse().up();

        String valueText = handle.getAttribute("aria-valuetext");
        String[] valueHour = valueText.split(":");
        int hourValue = Integer.parseInt(valueHour[0]);
        int minuteValue = Integer.parseInt(valueHour[1]);
        // System.out.println("el valor es: " + valueText);

        List<Locator> departureTimes = page.locator("[data-testid='trip-bound-2-info-departureTime']").all();
        for (Locator time : departureTimes) {
            String timeText = time.textContent().trim();

            String[] parts = timeText.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);

            if (hours < hourValue || (hours == hourValue && minutes < minuteValue)) {
                throw new RuntimeException("Test fallido: Se encontró un vuelo con salida antes de las " + hourValue
                        + ":" + minuteValue + " -> " + timeText);
            }
        }

        System.out.println("Test completado correctamente: Todos los vuelos vuelven antes de las  " + valueText + ".");
    }

    public void resetValueDepartureSection() {
        Locator salida = page.locator("[data-testid='resultPage-departureArrivalFilter-departure1-radio']");
        salida.scrollIntoViewIfNeeded();

        Locator sliderContainer = page.locator("[data-testid='resultPage-departureArrivalFilter-departure1-slider']");
        sliderContainer.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Locator allHandles = sliderContainer.locator("input");

        Locator handle = allHandles.nth(1);
        // Obtenido de ChatGPT
        BoundingBox box = handle.boundingBox();

        // Calcular nueva posición: mover el handle hacia la izquierda
        double newX = box.x + box.width * 0.70; // por ejemplo, moverlo al 20%
        double centerY = box.y + box.height / 2;

        // Simular arrastre con mouse
        page.mouse().move(box.x + box.width - 1, centerY); // ir al extremo derecho
        page.mouse().down();
        page.mouse().move(newX, centerY);
        page.mouse().up();

        int value = Integer.parseInt(handle.evaluate("el => el.value").toString());

        // System.out.println("Handle izquierdo: " + handle.evaluate("el => el.value"));
        page.waitForSelector("[data-testid='resultPage-filterHeader-departureArrival1FilterResetButton-button']",
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        page.locator("[data-testid='resultPage-filterHeader-departureArrival1FilterResetButton-button']").click();

        int newValueIzq = Integer.parseInt(handle.evaluate("el => el.value").toString());

        if (value == newValueIzq) {
            throw new RuntimeException("Test fallido: No se modificaron los valores al resetearlos.");
        } else {
            System.out.println("Test completado correctamente: Los valores han sido reseteados correctamente.");
        }
    }

    public void changeArrivalSection() {

        page.locator("[data-testid='resultPage-departureArrivalFilter-arrival0-radio']").click();

        Locator sliderContainer = page.locator("[data-testid='resultPage-departureArrivalFilter-arrival0-slider']");
        sliderContainer.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        Locator allHandles = sliderContainer.locator("input");

        Locator handleDerecho = allHandles.nth(1);
        BoundingBox box = handleDerecho.boundingBox();

        // Calcular nueva posición: mover el handle hacia la izquierda
        double newX = box.x + box.width * 0.70; // por ejemplo, moverlo al 20%
        double centerY = box.y + box.height / 2;

        // Simular arrastre con mouse
        page.mouse().move(box.x + box.width - 1, centerY); // ir al extremo derecho
        page.mouse().down();
        page.mouse().move(newX, centerY);
        page.mouse().up();
        String value = handleDerecho.getAttribute("aria-valuetext");
        // System.out.println("el valor es: " + value);
        String[] valueHour = value.split(":");
        int hourValue = Integer.parseInt(valueHour[0]);
        int minuteValue = Integer.parseInt(valueHour[1]);

        page.waitForTimeout(3000);

        List<Locator> arrivalTimes = page.locator("[data-testid='trip-bound-1-info-arrivalTime']").all();

        for (Locator time : arrivalTimes) {
            String timeText = time.textContent().replace("+2", "").replace("+1", "").trim();
            String[] parts = timeText.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1].replaceAll("\\D", "").trim());

            if (hour > hourValue || (hour == hourValue && minute > minuteValue)) {
                page.waitForTimeout(3000);
                throw new RuntimeException("Test fallido: Vuelo llega después de -> " + timeText);
            }
        }
        System.out.println("Test completado correctamente: Todos los vuelos llegan antes o a las " + value);
    }

}