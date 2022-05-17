package com.example.demo.service;



import com.example.demo.entity.Document;
import com.example.demo.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Stream;

import static org.springframework.util.StringUtils.cleanPath;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public Document store(MultipartFile file)throws IOException {
            String fileName= cleanPath(file.getOriginalFilename());
            Document document = new Document(fileName, file.getSize(), file.getBytes());

            return documentRepository.save(document);
    }

    public Document getDocument(Long id){
        return documentRepository.findById(id).get();
    }

    public Stream<Document> getAllDocuments(){
        return documentRepository.findAll().stream();
    }
}
