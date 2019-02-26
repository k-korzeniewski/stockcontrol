package com.kamilkorzeniewski.stockcontrol.invoice;

import com.kamilkorzeniewski.stockcontrol.exception.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class InvoiceStorageService {

    private final Path fileStoragePath;

    public InvoiceStorageService(InvoiceStorageProperties invoiceStorageProperties) {
        this.fileStoragePath = Paths.get(invoiceStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStoragePath);
        } catch (IOException ex) {
            throw new FileStorageException("Cant create directories");
        }

    }

    Path storeFile(MultipartFile file) {
        String fileName = LocalDateTime.now().withNano(0).toString();
        Path targetLoc = fileStoragePath.resolve(fileName);
        try {
            Files.copy(file.getInputStream(),targetLoc,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Cant save file");
        }

        return targetLoc;
    }
}
