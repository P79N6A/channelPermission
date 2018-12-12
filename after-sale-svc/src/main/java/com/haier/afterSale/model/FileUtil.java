package com.haier.afterSale.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileUtil {
    private static org.apache.log4j.Logger log          = org.apache.log4j.LogManager
            .getLogger(FileUtil.class);
    @Value("${wsdlPath}")
    private String wsdlLocation;

    public void setWsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
    }

    public String getWsdlPath(String wsdlFile) {
        String path = "file:"
                + this.getClass().getResource(wsdlLocation + "/" + wsdlFile).getPath();
        if (log.isDebugEnabled())
            log.debug("wsdl path:" + path);
        return path;
    }
}
