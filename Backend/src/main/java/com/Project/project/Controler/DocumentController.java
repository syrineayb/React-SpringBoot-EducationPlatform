package com.Project.project.Controler;

import com.Project.project.Repository.DocumentRepo;
import com.Project.project.ServiceImp.DocumentServiceImp;
import com.Project.project.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DocumentController {
    @Autowired
    private DocumentServiceImp DocService;
    @Autowired
    private DocumentRepo dorepo;

    @GetMapping("/getDocument")
    public List<Document> ListALLDocuments(){
        return this.DocService.listAllDocument();
    }

    @GetMapping("/findDocumentbyid/{DocumentID}")
    public ResponseEntity<Document> getCateByIdCat(@PathVariable("DocumentID") int DocumentID) {
        Document teach = DocService.getDocumentById(DocumentID);
        return new ResponseEntity<>(teach, HttpStatus.OK);
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long documentId) {
        // Retrieve the Document entity from the database
        Document document = dorepo.findById(documentId).orElse(null);

        if (document != null) {
            // Extract the binary data from the Document entity
            byte[] content = document.getContent();
            String fileName = document.getName();
            String contentType = document.getContentType();

            // Create a temporary file to store the content
            try {
                File tempFile = File.createTempFile(fileName, null);
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(content);
                fos.close();

                // Create a Resource object from the temporary file
                Resource resource = new FileSystemResource(tempFile);

                // Prepare the response with appropriate headers
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
                headers.add(HttpHeaders.CONTENT_TYPE, contentType);

                // Return the file as a ResponseEntity
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle any errors that may occur during file creation or writing
            }
        }

        // If the document is not found or any error occurs, return an appropriate response
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/addDocument")
    public ResponseEntity<Document> add(@RequestBody Document Document) {
        Document newteach = DocService.addDocument(Document);
        return new ResponseEntity<>(newteach, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteDocument/{DocumentID}")
    public ResponseEntity<?> deleteDocumentBYID(@PathVariable("DocumentID") int DocumentID) {
        DocService.deleteDocument(DocumentID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("updateDocument/{DocumentID}")
    public Document updateDocumentById(@PathVariable Long DocumentID, @RequestBody Document updatedDocument) {
        return DocService.update(DocumentID, updatedDocument);
    }
}
