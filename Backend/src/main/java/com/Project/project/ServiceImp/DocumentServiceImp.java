package com.Project.project.ServiceImp;

import com.Project.project.Repository.DocumentRepo;
import com.Project.project.Service.DocumentService;
import com.Project.project.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class DocumentServiceImp implements DocumentService {
    @Autowired
    private DocumentRepo repository;
    public List<Document> listAllDocument() {
        if(repository == null) {
            throw new RuntimeException("DocumentRepo is null");
        }
        return repository.findAll();
    }


    @Override
    public Document getDocumentById(int idDocument) {
        return repository.findById((long) idDocument).get();
    }

    @Override
    public Document addDocument(Document Doc) {
        return repository.save(Doc);
    }

    @Override
    public void deleteDocument(int id) {
        repository.deleteById((long) id);
    }
    @Override
    public Document update(long DocumentId, Document Document) {
        Optional<Document> optionalDocument = repository.findById(DocumentId);
        if (optionalDocument.isPresent()) {
            Document existingDocument = optionalDocument.get();
            existingDocument.setName(Document.getName());
            existingDocument.setContent(Document.getContent());
            existingDocument.setName(Document.getName());
            return repository.save(existingDocument);
        } else {
            return null;
        }
    }

}
