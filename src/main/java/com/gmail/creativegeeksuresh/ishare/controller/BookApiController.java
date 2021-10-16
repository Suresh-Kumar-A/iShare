package com.gmail.creativegeeksuresh.ishare.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.gmail.creativegeeksuresh.ishare.dto.BookDto;
import com.gmail.creativegeeksuresh.ishare.dto.BookRequestDto;
import com.gmail.creativegeeksuresh.ishare.dto.CustomErrorResponse;
import com.gmail.creativegeeksuresh.ishare.exception.BookAlreadyExistsException;
import com.gmail.creativegeeksuresh.ishare.exception.InvalidBookException;
import com.gmail.creativegeeksuresh.ishare.model.Book;
import com.gmail.creativegeeksuresh.ishare.service.BookRequestService;
import com.gmail.creativegeeksuresh.ishare.service.BookService;
import com.gmail.creativegeeksuresh.ishare.service.util.CustomPdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/book")
public class BookApiController {

    @Autowired
    CustomPdfService customPdfService;

    @Autowired
    BookService bookService;

    @Autowired
    BookRequestService bookRequestService;

    @PostMapping(value = "/content/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookContents(@RequestParam String uid, @RequestParam("book") MultipartFile file) {
        try {
            customPdfService.createFile(file.getInputStream(), file.getOriginalFilename(), uid);
            return new ResponseEntity<>(Map.of("status", true, "message", "Book Uploaded successfully"),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(
                    new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), ""),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // #toolbar=0 -- use to prevent download option in chrome browser
    @GetMapping(value = "/content/view")
    public ResponseEntity<?> readBookInPdf(@RequestParam(value = "uid") String uid) {
        try {
            Book requestedBook = bookService.findByUid(uid);
            if (requestedBook != null && requestedBook.getAvailable() && !requestedBook.getLocation().isEmpty()) {
                Path path = Paths.get(requestedBook.getLocation()).toAbsolutePath().normalize();
                Resource resource = new UrlResource(path.toUri());
                return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/pdf")).body(resource);
            } else {
                throw new Exception("Requested Book unavailable :" + uid);
            }
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(
                    new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), ""),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addBookEntry(@RequestBody BookDto request) {
        try {
            return new ResponseEntity<>(bookService.addBook(request), HttpStatus.CREATED);
        } catch (BookAlreadyExistsException baex) {
            System.err.println(baex.getLocalizedMessage());
            return new ResponseEntity<>(
                    new CustomErrorResponse(HttpStatus.CONFLICT.value(), baex.getLocalizedMessage(), ""),
                    HttpStatus.CONFLICT);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(
                    new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), ""),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updateBookEntry(@RequestBody BookDto request) {
        try {
            return new ResponseEntity<>(bookService.updateBook(request), HttpStatus.OK);
        } catch (InvalidBookException ibex) {
            System.err.println(ibex.getLocalizedMessage());
            return new ResponseEntity<>(
                    new CustomErrorResponse(HttpStatus.CONFLICT.value(), ibex.getLocalizedMessage(), ""),
                    HttpStatus.CONFLICT);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(
                    new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), ""),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/view-all")
    public ResponseEntity<?> getAllBooks() {
        try {
            return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/view")
    public ResponseEntity<?> viewBookInfo(@RequestParam String uid) {
        try {
            // bookService.deleteBookByUid(uid);
            return new ResponseEntity<>(bookService.findByUid(uid), HttpStatus.OK);
        }  catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteBookAccount(@RequestParam String uid) {
        try {
            bookService.deleteBookByUid(uid);
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/add-book-request")
    public ResponseEntity<?> addBookAcessRequest(@RequestBody BookRequestDto request) {
        try {
            return new ResponseEntity<>(bookRequestService.createRequest(request), HttpStatus.CREATED);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/allow-book-access")
    public ResponseEntity<?> allowBookAccess(@RequestParam String uid) {
        try {
            bookRequestService.updateBookAvailablityAndDeleteRequest(uid);
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        }  catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
