package com.example.junitfirst;

import lombok.Setter;

public class StockManager {
    @Setter
    private ExternalISBNDataService externalISBNDataService;

    public String getLocatorCode(String isbn) {
        // call service
        Book book = externalISBNDataService.lookup(isbn);

        // logic
        StringBuilder locator = new StringBuilder();
        locator.append(isbn.substring(isbn.length() - 4));
        locator.append(book.getAuthor().substring(0, 1));
        locator.append(book.getTitle().split(" ").length);

        return locator.toString();
    }
}
