package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.model.entity.CanceledInvoice;
import com.ingroup.invoice_web.model.entity.InvoiceDetail;
import com.ingroup.invoice_web.model.entity.InvoiceMain;
import com.ingroup.invoice_web.model.entity.VoidedInvoice;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class XmlGeneratorService {


    private final freemarker.template.Configuration freemarkerConfig;

    public XmlGeneratorService(
            freemarker.template.Configuration freemarkerConfig
    ) {
        this.freemarkerConfig = freemarkerConfig;
    }

    public String generateInvoiceXML(InvoiceMain invoiceMain, List<InvoiceDetail> invoiceDetailList) throws IOException, TemplateException {
        // 準備資料模型
        Map<String, Object> model = new HashMap<>();
        model.put("invoiceMain", invoiceMain);
        model.put("invoiceDetailList", invoiceDetailList);

        // 載入模板
        Template template = freemarkerConfig.getTemplate("migxml/f0401.xml.ftl");

        // 合併模板與資料
        StringWriter writer = new StringWriter();
        template.process(model, writer);

        return writer.toString(); // 可改成輸出成檔案
    }


    public String generateCanceledInvoiceXML(CanceledInvoice canceledInvoice,String SourceMigType) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("canceledInvoice", canceledInvoice);
        String templatePath;
        switch (SourceMigType) {
            case "A0401":
                templatePath = "migxml/a0201.xml.ftl";
            case "F0401":
                templatePath = "migxml/f0501.xml.ftl";
            default:
                templatePath = "未知來源格式";
        }

        Template template = freemarkerConfig.getTemplate(templatePath);

        StringWriter writer = new StringWriter();
        template.process(model, writer);

        return writer.toString(); // 可改成輸出成檔案
    }

    public String  generateVoidedInvoiceXML(VoidedInvoice voidedInvoice) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("voidedInvoice", voidedInvoice);
        String templatePath =  "migxml/f0701.xml.ftl";;
        Template template = freemarkerConfig.getTemplate(templatePath);

        StringWriter writer = new StringWriter();
        template.process(model, writer);

        return writer.toString(); // 可改成輸出成檔案
    }

}
