package com.ingroup.invoice_web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private String outputUpcast;
    private String inputDownCast;

    public String getOutputUpcast() {
        return outputUpcast;
    }

    public void setOutputUpcast(String outputUpcast) {
        this.outputUpcast = outputUpcast;
    }

    public String getInputDownCast() {
        return inputDownCast;
    }

    public void setInputDownCast(String inputDownCast) {
        this.inputDownCast = inputDownCast;
    }


}
