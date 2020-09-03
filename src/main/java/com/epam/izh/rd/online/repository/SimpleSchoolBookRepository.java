package com.epam.izh.rd.online.repository;

import com.epam.izh.rd.online.entity.SchoolBook;

import java.util.Arrays;

public class SimpleSchoolBookRepository implements BookRepository<SchoolBook> {

    private SchoolBook[] schoolBooks = new SchoolBook[0];

    @Override
    public boolean save(SchoolBook book) {
        if (findByName(book.getName()) != null) {
            SchoolBook[] schoolBooksCopy = Arrays.copyOf(schoolBooks, schoolBooks.length + 1);
            schoolBooksCopy[schoolBooksCopy.length - 1] = book;
            schoolBooks = schoolBooksCopy;
            return true;
        }
        return false;
    }

    @Override
    public SchoolBook[] findByName(String name) {
        for (SchoolBook book : schoolBooks) {
            if (book.getName().equals(name)) {
                return schoolBooks;
            }
        }
        return new SchoolBook[0];
    }

    @Override
    public boolean removeByName(String name) {
        SchoolBook[] removed = findByName(name);
        if (removed.length == 0) {
            return false;
        }
        SchoolBook[] temp = new SchoolBook[schoolBooks.length - removed.length];
        int counter = 0;
        for (SchoolBook book : schoolBooks) {
            boolean isRemoved = false;
            for (SchoolBook removedBook : removed) {
                if (book.equals(removedBook)) {
                    isRemoved = true;
                    break;
                }
            }
            if (!isRemoved) {
                temp[counter++] = book;
            }
        }
        schoolBooks = temp;
        return true;
    }

    @Override
    public int count() {
        return schoolBooks.length;
    }
}
