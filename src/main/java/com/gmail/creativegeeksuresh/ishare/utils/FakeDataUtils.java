package com.gmail.creativegeeksuresh.ishare.utils;

import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.javafaker.Faker;
import com.gmail.creativegeeksuresh.ishare.model.Book;
import com.gmail.creativegeeksuresh.ishare.model.Role;
import com.gmail.creativegeeksuresh.ishare.model.User;
import com.gmail.creativegeeksuresh.ishare.service.util.CustomUtils;

public final class FakeDataUtils {

    static final int userCount = 10;
    static final int bookCount = 10;
    /**
     * Actual Password: P@ss123
     */
    static final String DEFAULT_PWD = "$2a$12$G/eO8KWMtICWP03d5794ruaB1wzg0/Q1/Pgmr709.3iSGDaVxU.XW";

    public static List<User> generateFakeUsers(Role userRole) throws Exception {
        Faker faker = new Faker();
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            String displayName = faker.name().fullName();
            User user = User.builder().displayName(displayName).username(faker.name().username())
                    .emailAddress(displayName.concat("@ishare.com"))
                    .password(DEFAULT_PWD).uid(new CustomUtils().generateToken()).role(userRole)
                    .createdAt(new Date()).build();
            userList.add(user);
        }
        return userList;
    }

    public static List<Book> generateFakeBooks() throws Exception {
        Faker faker = new Faker();
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            Book book = Book.builder().author(faker.book().author()).uid(new CustomUtils().generateToken())
                    .title(faker.book().title()).description(faker.book().genre())
                    .publishedYear(Year.now()).build();
            bookList.add(book);
        }
        return bookList;
    }

}
