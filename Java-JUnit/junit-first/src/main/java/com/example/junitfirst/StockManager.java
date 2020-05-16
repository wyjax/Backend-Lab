package com.example.junitfirst;

import lombok.Setter;

public class StockManager {

    // set Database Service
    @Setter
    private ExternalISBNDataService databaseService;

    // set Web Service
    @Setter
    private ExternalISBNDataService webService;

    public String getLocatorCode(String isbn) {
        // check db have book info
        Book book = databaseService.lookup(isbn);

        // DB에 책 정보가 없다면 웹서비스 호출
        if (book == null) {
            book = webService.lookup(isbn);
        }

        // business logic
        StringBuilder locator = new StringBuilder();
        locator.append(isbn.substring(isbn.length() - 4));
        locator.append(book.getAuthor().substring(0, 1));
        locator.append(book.getTitle().split(" ").length);

        // return
        return locator.toString();
    }

}