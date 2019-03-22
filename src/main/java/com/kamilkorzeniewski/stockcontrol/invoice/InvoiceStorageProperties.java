package com.kamilkorzeniewski.stockcontrol.invoice;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "invoice")
public class InvoiceStorageProperties {
    private String uploadDir;
    private String filePrefix;
    public String getUploadDir() {
        return uploadDir;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
