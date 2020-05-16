package com.example.junitfirst;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockManagementTest {

    @Test
    public void canGetCorrectLocatorCode() {
        // Stub
        ExternalISBNDataService service = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return (new Book(isbn, "Of Mice And Men", "J. Streinbeck"));
            }
        };

        // set service
        StockManager stockManager = new StockManager();
        stockManager.setExternalISBNDataService(service);

        // do
        String isbn = "014077396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        // assert
        Assertions.assertThat("7396J4").isEqualTo(locatorCode);
    }
}
