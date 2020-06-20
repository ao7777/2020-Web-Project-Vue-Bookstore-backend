package org.reins.orm.dao.impl;

import org.reins.orm.dao.BookDao;
import org.reins.orm.entity.BookEntity;
import org.reins.orm.entity.BookInfo;
import org.reins.orm.repository.BookInfoRepository;
import org.reins.orm.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    private BookRepository bookRepository;
    private BookInfoRepository bookInfoRepository;
    @Autowired
    public BookDaoImpl(BookInfoRepository bookInfoRepository,BookRepository bookRepository)
    {
        this.bookInfoRepository=bookInfoRepository;
        this.bookRepository=bookRepository;
    }
    @Override
    public List getBooks() {
        List<BookEntity> list=bookRepository.findAll();
        list.forEach(
                book->{
                    book.setBookInfo(bookInfoRepository.findByIsbn(Long.parseLong(
                            book.getIsbn()
                    )).orElse(null));
        }
        );
        return list;
    }

    @Override
    public void updateBook(BookEntity bookEntity) {
        BookEntity book=getBookByISBN(bookEntity.getIsbn());
        book.setAuthor(bookEntity.getAuthor());
        book.setName(bookEntity.getName());
        book.setPress(bookEntity.getPress());
        book.setPrice(bookEntity.getPrice());
        book.setPublishDate(bookEntity.getPublishDate());
        book.setSales(bookEntity.getSales());
        bookInfoRepository.save(bookEntity.getBookInfo());
        bookRepository.save(book);
    }



    @Override
    public BookEntity getBookByISBN(String isbn) {
        BookEntity bookEntity=bookRepository.getOne(isbn);
        bookEntity.setBookInfo(bookInfoRepository.findByIsbn(Long.parseLong(isbn)).orElse(null));
        return bookEntity;
    }

    @Override
    public BookInfo getBookInfo(Long isbn) {
        return bookInfoRepository.findByIsbn(isbn).orElse(null);
    }

    @Override
    public void addBook(BookEntity bookEntity) {
        bookRepository.save(bookEntity);
        if(bookEntity.getBookInfo()!=null){
            bookInfoRepository.save(bookEntity.getBookInfo());
        }
    }

    @Override
    public void deleteBookByISBN(String isbn) {
        bookRepository.deleteById(isbn);
        bookInfoRepository.deleteBookInfoByIsbn(Long.parseLong(isbn));
    }

}
