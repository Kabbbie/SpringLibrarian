package ru.milovanov.SpringLibrarian.ajax;

import ru.milovanov.SpringLibrarian.dao.objects.Book;

import java.util.List;

public class Page <T>{

    public List<T> page;
    public int totalRecords;

    public Page(List<T> page, int totalRecords) {
        this.page = page;
        this.totalRecords = totalRecords;
    }
}
