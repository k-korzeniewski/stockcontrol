package com.kamilkorzeniewski.stockcontrol.invoice;

import com.kamilkorzeniewski.stockcontrol.exception.FileStorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class InvoiceStorageService {

    private final Path fileStoragePath;
    private final String filePrefix;

    public InvoiceStorageService(InvoiceStorageProperties invoiceStorageProperties) {
        this.fileStoragePath = Paths.get(invoiceStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.filePrefix = invoiceStorageProperties.getFilePrefix();

        try {
            Files.createDirectories(this.fileStoragePath);
        } catch (IOException ex) {
            throw new FileStorageException("Can not create directories", ex);
        }

    }

    Path storeFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!Objects.equals(extension, "csv")) {
            throw new FileStorageException("Wrong file extension ." + extension);
        }
        String timestamp = LocalDateTime.now().withNano(0).toString();
        String fileName = filePrefix+ "_" + timestamp + "." + extension;
        Path targetLoc = fileStoragePath.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLoc, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Cant save file", ex);
        }
        return targetLoc.getFileName();
    }

    public Path getFileStoragePath(String fileName){
        return fileStoragePath.resolve(fileName);
    }
}
