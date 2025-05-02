package etraveli;

import java.util.function.Consumer;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;

public class api {
    Page page;
    final boolean[] isExecuted = {false};

    api() {
        // Constructor vacío
    }

    api(Page page) {
        this.page = page;
    }

    public void apitest() {
        page.waitForSelector("[data-testid='resultPage-filtersContainer']");
        page.onResponse(response -> {
            if (!isExecuted[0] && response.url().contains("/graphql/SearchOnResultPage") && response.request().method().equals("POST")) {
                isExecuted[0] = true; // Marcar como ejecutado
            try {
                    if (response.status() != 200) {
                        throw new RuntimeException(
                                "La respuesta esperada era un 200 pero se obtuvo " + response.status());
                    }

                    String json = response.text();
                    System.out.println(
                            "Test completado correctamente: Se recibió la respuesta esperada: " + response.status());

                    if (!json.contains("flights") || json.contains("errors")) {
                        throw new RuntimeException(
                                " Test fallido: Respuesta inválida: faltan datos esperados o contiene errores");
                    }
                    
                } catch (Exception e) {
                    System.err.println(" Excepción al procesar la respuesta: " + e.getMessage());
                }
            }
        });

        page.locator("[data-testid='MAX_STOPS-max']").click();

        page.waitForTimeout(3000);
    }
}