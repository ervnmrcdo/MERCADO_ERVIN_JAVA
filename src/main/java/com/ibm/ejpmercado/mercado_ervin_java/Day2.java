package com.ibm.ejpmercado.mercado_ervin_java;

import java.util.ArrayList;


class Book {
    private String title;
    private String author;
    private boolean available;

    public Book(String title, String author, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

    void borrowBook() {
        if (this.available) {
            this.available = false;
        } else {
            System.out.println("Book is already borrowed");
        }
    }

    void returnBook() {
        this.available = true;

    }


    
    void getInfo() {
        System.out.println("Name:" + this.title + " Author: " + this.author + " Availability: " + this.available);
    }

    String getTitle() {
        return this.title;
    }

}

class Library {
    ArrayList<Book> books = new ArrayList<>();

    //

    void addBook(Book b) {
        this.books.add(b);
    }

    void showAllBooks() {
        for (Book b : books) {
            b.getInfo();
        }
    }

    void borrowBook(String title) {
        for (Book b : books) {
            if (b.getTitle() == title) {
                System.out.println("--BORROWING BOOK--");
                b.borrowBook();
                return;
            }
        }
        System.out.println("Book Not Found" + title);
    }

    void returnBook(String title) {
        for (Book b : books) {
            if (b.getTitle() == title) {
                System.out.println("-- RETURNING BOOK--");
                b.returnBook();
                return;
            }
        }
        System.out.println("Book Not Found" + title);
    }

}

public class Day2 {

    public static void main(String[] args) {

        Library library = new Library();
        Book book1 = new Book("book1", "author1", true);
        Book book2 = new Book("book2", "author2", true);
        Book book3 = new Book("book3", "author3", true);

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.showAllBooks();
        System.out.println("");

        library.borrowBook("book1");
        library.showAllBooks();
        System.out.println("");

        library.returnBook("book1");
        library.showAllBooks();
        System.out.println("");

        library.borrowBook("yo");

    }

}
