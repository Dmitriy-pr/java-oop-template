package com.epam.izh.rd.online.repository;

import com.epam.izh.rd.online.entity.Author;

import java.util.Arrays;

public class SimpleAuthorRepository implements AuthorRepository {

    private Author[] authors = new Author[0];

    @Override
    public boolean save(Author author) {
        boolean result = false;
        if (findByFullName(author.getName(), author.getLastName()) == null) {
            authors = Arrays.copyOf(authors, authors.length + 1);
            authors[authors.length - 1] = author;
            result = true;
        }
        return result;
    }

    @Override
    public Author findByFullName(String name, String lastname) {
        Author result = null;
        for (int index = 0; index < authors.length; index++) {
            if (authors[index].getName()
                    .equals(name) && authors[index].getLastName()
                    .equals(lastname)) {
                result = authors[index];
                break;
            }
        }
        return result;
    }

    @Override
    public boolean remove(Author author) {
        Author removed = findByFullName(author.getName(), author.getLastName());
        if (removed == null) {
            return false;
        }
        Author[] temp = new Author[authors.length - 1];
        int counter = 0;
        for (Author a : authors) {
            if (!a.equals(removed)) {
                temp[counter++] = a;
            }
        }
        authors = temp;
        return true;
    }

    @Override
    public int count() {
        return authors.length;
    }
}
