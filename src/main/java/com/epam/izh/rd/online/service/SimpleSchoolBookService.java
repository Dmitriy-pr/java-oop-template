package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Author;
import com.epam.izh.rd.online.entity.Book;
import com.epam.izh.rd.online.entity.SchoolBook;
import com.epam.izh.rd.online.repository.BookRepository;

import java.util.Objects;

public class SimpleSchoolBookService implements BookService {

    private BookRepository<SchoolBook> schoolBookBookRepository;
    private AuthorService authorService;

    public SimpleSchoolBookService() {
    }

    public SimpleSchoolBookService(BookRepository<SchoolBook> schoolBookBookRepository, AuthorService authorService) {
        this.schoolBookBookRepository = schoolBookBookRepository;
        this.authorService = authorService;
    }

    @Override
    public boolean save(Book book) {
        boolean result = false;
        if (!Objects.isNull(authorService.findByFullName(((SchoolBook) book).getAuthorName(),
                ((SchoolBook) book).getAuthorLastName()))) {
            schoolBookBookRepository.save((SchoolBook) book);
            result = true;
        }
        return result;
    }

    @Override
    public Book[] findByName(String name) {
        return schoolBookBookRepository.findByName(name);
    }

    @Override
    public int getNumberOfBooksByName(String name) {
        SchoolBook[] schoolBooks = schoolBookBookRepository.findByName(name);
        int count = 0;
        for (SchoolBook book : schoolBooks) {
            if (book.getName().equals(name)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean removeByName(String name) {
        return schoolBookBookRepository.removeByName(name);
    }

    @Override
    public int count() {
        return schoolBookBookRepository.count();
    }

    @Override
    public Author findAuthorByBookName(String name) {
        SchoolBook[] books = (SchoolBook[]) findByName(name);
        for (int index = 0; index < books.length; index++) {
            if (books[index].getName().equals(name)) {
                String authorName = books[index].getAuthorName();
                String authorLastName = books[index].getAuthorLastName();
                return authorService.findByFullName(authorName, authorLastName);
            }
        }
        return null;
    }
}
