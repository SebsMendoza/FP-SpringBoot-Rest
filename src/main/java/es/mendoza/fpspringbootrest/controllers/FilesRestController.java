package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.errors.storage.StorageException;
import es.mendoza.fpspringbootrest.service.uploads.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/")
public class FilesRestController {
    private StorageService storageService;

    @Autowired
    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = "files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request) {
        Resource file = storageService.loadAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException e) {
            throw new StorageException("No se puede determinar el tipo de fichero", e);
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

    @PostMapping(value = "files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestPart("file") MultipartFile file) {
        String urlImagen = null;

        try {
            if (!file.isEmpty()) {
                String imagen = storageService.store(file);
                urlImagen = storageService.getUrl(imagen);
                Map<String, Object> response = Map.of("url", urlImagen);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                throw new StorageException("No se puede subir un fichero vacío");
            }
        } catch (StorageException e) {
            throw new StorageException("No se puede subir un fichero vacío");
        }
    }
}
