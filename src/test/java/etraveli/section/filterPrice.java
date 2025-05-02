package etraveli.section;

import java.util.List;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.WaitForSelectorState;

public class filterPrice {
    Page page;

    public filterPrice() {
        // Constructor vacío
    }

    public filterPrice(Page page) {
        this.page = page;

    }

    public void ChangeDownPrice() {
        Locator sliderContainer = page.locator("[data-testid='resultPage-PRICEFilter-content']");
        sliderContainer.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Locator allHandles = sliderContainer.locator("input");

        Locator handle = allHandles.nth(1);
        // Este codigo lo saque de ChatGPT
        BoundingBox box = handle.boundingBox();

        // Calcular nueva posición: mover el handle hacia la izquierda
        double newX = box.x + box.width * 0.70; // por ejemplo, moverlo al 20%
        double centerY = box.y + box.height / 2;

        // Simular arrastre con mouse
        page.mouse().move(box.x + box.width - 1, centerY); // ir al extremo derecho
        page.mouse().down();
        page.mouse().move(newX, centerY);
        page.mouse().up();
        // Hasta aca
        Double value = Double.parseDouble(handle.evaluate("el => el.value").toString());

        // System.out.println("Handle izquierdo: " + handle.evaluate("el => el.value"));

        page.waitForSelector("[data-testid='standard-price']", new Page.WaitForSelectorOptions().setTimeout(10000));

        List<Locator> listPrices = page.locator("[data-testid='standard-price']").all();
        for (Locator price : listPrices) {
            String texto = price.innerText().replaceAll("\\D", "").trim(); // Eliminar caracteres no numéricos.trim();
            if (Double.parseDouble(texto) > value) {
                throw new RuntimeException("Failed test: the price is higher than the expected value. " + value);
            }
        }
        System.out
                .println("Test completed successfully: all options have a price less than or equal to " + value);
    }

}