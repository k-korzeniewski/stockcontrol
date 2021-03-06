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
        String uploadDir = invoiceStorageProperties.getUploadDir();
        this.fileStoragePath = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.filePrefix = invoiceStorageProperties.getFilePrefix();
        try {
            Files.createDirectories(this.fileStoragePath);
        } catch (IOException ex) {
            throw new FileStorageException("Can not create directories", ex);
        }

    }

    public String getFileStoragePath(String fileName){
        return fileStoragePath.resolve(fileName).toString();
    }

    Path storeFile(MultipartFile file) {
        if (!Objects.equals(getExtension(file), "csv")) {
            throw new FileStorageException("Wrong file extension ." + getExtension(file));
        }
        Path targetLoc = fileStoragePath.resolve(generateName(file));
        try {
            Files.copy(file.getInputStream(), targetLoc, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Cant save file:"+file.getOriginalFilename(), ex);
        }
        return targetLoc.getFileName();
    }

    void removeFromStorage(String fileName){
        Path filePath = Paths.get(getFileStoragePath(fileName));
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new FileStorageException("Cant remove file (probably not exist) : "+fileName);
        }
    }
    private static String getExtension(MultipartFile file){return FilenameUtils.getExtension(file.getOriginalFilename());}

    private String generateName(MultipartFile file){
        String timestamp = LocalDateTime.now().withNano(0).toString();
        String fileName = filePrefix+ "_" + timestamp + "." + getExtension(file);
        return fileName;
    }
}
