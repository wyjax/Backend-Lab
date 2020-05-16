package com.example.junitfirst;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StockManagementTest {
    // 행동을 테스트 할 수 있는 Mock 객체를 사용

    /*
        - Verify
            행동을 검사하기 위해 Verify를 사용할 수 있다. Verify는 명시한 메서드를 특정
            Parameter n time 호출했는지 확인한다.
     */

    @Test
    public void databaseIsUsedIfDataIsPresent() {
        // given
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup("014077396")).thenReturn(new Book("014077396", "abc", "abc"));

        // when
        StockManager stockManager = new StockManager();
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);

        String isbn = "014077396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        // then
        /*
            verify는 databaseService의 lookup() 메서드가 파마레터 "014077396"로 몇 번 호출됐는지 검증할 수 있다.
            또헌 webService의 lookup() 메서드가 0번 호출됐는지 검증한다.
         */
        verify(databaseService, times(1)).lookup(isbn);
        verify(webService, times(0)).lookup(anyString());
    }

    @Test
    public void webserviceIsUsedIfDataIsNotPresentInDatabase() {
        // create ExternalISBNDataService instance
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        // set returun value
        when(databaseService.lookup("014077396")).thenReturn(null);
        when(webService.lookup("014077396")).thenReturn(new Book("014077396", "abc", "abc"));

        // set service
        StockManager stockManager = new StockManager();
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);

        // do
        String isbn = "014077396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        // verify
        verify(databaseService, times(1)).lookup(isbn);
        verify(webService, times(1)).lookup("014077396");
    }

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
        stockManager.setWebService(service);

        // do
        String isbn = "014077396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        // assert
        assertThat("7396J4").isEqualTo(locatorCode);
    }
}
