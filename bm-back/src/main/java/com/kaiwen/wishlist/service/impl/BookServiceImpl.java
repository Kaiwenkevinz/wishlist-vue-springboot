package com.kaiwen.wishlist.service.impl;

import com.kaiwen.wishlist.dao.BookDAO;
import com.kaiwen.wishlist.entity.Book;
import com.kaiwen.wishlist.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDAO bookDAO;

    @Override
    public List<Book> findAll() {
        return bookDAO.findAll();
    }

    @Override
    public void save(Book book) {
        bookDAO.save(book);
    }

    @Override
    public void delete(int id) {
        bookDAO.delete(id);
    }

    @Override
    public void update(Book book) {
        bookDAO.update(book);
    }

    @Override
    public List<Book> findByCategory(int cid) {
        return bookDAO.findByCategory(cid);
    }

    @Override
    public List<Book> selectBykeyWord(String keyword) {
        return bookDAO.selectBykeyWord(keyword);
    }
}
