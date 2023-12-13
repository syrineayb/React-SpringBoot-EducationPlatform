package com.Project.project.Service;

import com.Project.project.model.Document;

import java.util.List;

public interface DocumentService {
    List<Document> listAllDocument();
    Document getDocumentById(int idDocument);
    Document addDocument(Document Document);
    void deleteDocument(int id );
    Document update(long idDocument, Document Document);
}
