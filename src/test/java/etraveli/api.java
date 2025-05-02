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
                                "The expected response was 200 but received " + response.status());
                    }

                    String json = response.text();
                    System.out.println(
                            "Test completed successfully: The expected response was received: " + response.status());

                    if (!json.contains("flights") || json.contains("errors")) {
                        throw new RuntimeException(
                                " Test failed: Invalid response, missing expected data or contains errors");
                    }
                    
                } catch (Exception e) {
                    System.err.println(" Exception while processing the response: " + e.getMessage());
                }
            }
        });

        page.locator("[data-testid='MAX_STOPS-max']").click();

        page.waitForTimeout(3000);
    }
}