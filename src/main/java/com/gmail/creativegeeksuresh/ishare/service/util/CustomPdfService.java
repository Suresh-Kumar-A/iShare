package com.gmail.creativegeeksuresh.ishare.service.util;

import java.io.File;
import java.io.InputStream;

import com.gmail.creativegeeksuresh.ishare.service.BookService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CustomPdfService {

    @Value("${ishare.storage.path}")
    private String defaultStoragePath;

    @Autowired
    BookService bookService;

    public void createFile(InputStream inputStream, String fileName,String uid) throws Exception {
        File outputFile = new File(defaultStoragePath + fileName);
        PDDocument document = PDDocument.load(inputStream);
        document.setAllSecurityToBeRemoved(Boolean.TRUE);
        document.save(outputFile);
        bookService.updateBookPath(uid, outputFile.getAbsolutePath());
    }

}
