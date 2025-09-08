package com.qcadoo.mes.rew52.print;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.localization.api.utils.DateUtils;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.report.api.FontUtils;
import com.qcadoo.report.api.Footer;
import com.qcadoo.report.api.pdf.PdfHelper;
import com.qcadoo.report.api.pdf.PdfPageNumbering;
import com.qcadoo.report.api.pdf.ReportPdfView;

@Component("employeeLabelsReportPdf")
public class EmployeeLabelsReportPdf extends ReportPdfView {

    private static final String L_ID = "id";
    private static final String L_IDS = "ids";

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private PdfHelper pdfHelper;

    @Override
    protected String addContent(final Document document, final Map<String, Object> model, final Locale locale,
                                final PdfWriter writer) throws DocumentException, IOException {
        checkState(model.get(L_IDS) != null, "Unable to generate report - missing ids");

        List<Long> employeeIds = (List<Long>) model.get(L_IDS);
        List<Entity> employees = getEmployeesFromIds(employeeIds);

        PdfPTable table = pdfHelper.createPanelTable(2); // 2 columns
        table.setTableEvent(null);

        int index = 0;

        for (Entity employee : employees) {
            PdfPCell cell = new PdfPCell();
            cell.setFixedHeight(165F);

            cell.addElement(createLabel(writer, employee));
            table.addCell(cell);

            index++;

            if (index % 8 == 0) {
                document.add(table);
                if (index < employees.size()) {
                    document.add(Chunk.NEXTPAGE);
                    table = pdfHelper.createPanelTable(2);
                    table.setTableEvent(null);
                }
            } else if (index == employees.size()) {
                table.completeRow();
                document.add(table);
            }
        }

        return translationService.translate("rew52.employeeLabelsReport.report.fileName", locale,
                DateUtils.toDateTimeString(new Date()));
    }

    private PdfPTable createLabel(final PdfWriter writer, final Entity employee) {
        PdfPTable labelTable = new PdfPTable(1);

        labelTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        labelTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        labelTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        labelTable.getDefaultCell().setPaddingTop(10F);
        labelTable.getDefaultCell().setPaddingBottom(10F);

        createFullNameAndCode(writer, labelTable, employee);

        labelTable.getDefaultCell().setPaddingTop(0F);
        labelTable.getDefaultCell().setPaddingLeft(30F);
        labelTable.getDefaultCell().setPaddingRight(30F);
        labelTable.getDefaultCell().setPaddingBottom(0F);

        createBarcode(writer, labelTable, employee);

        return labelTable;
    }

    private void createFullNameAndCode(final PdfWriter writer, final PdfPTable barcodeTable, final Entity employee) {
        String fullName = employee.getStringField("fullName");
        String code = employee.getStringField("code");
        String departmentName = employee.getStringField("departmentName");
        String positionName = employee.getStringField("positionName");

        // Tạo nội dung thông tin nhân viên
        Phrase employeeInfo = new Phrase();
        employeeInfo.add(new Chunk("Họ tên: ", FontUtils.getDejavuBold10Dark()));
        employeeInfo.add(new Chunk(fullName + "\n", FontUtils.getDejavuRegular10Dark()));

        employeeInfo.add(new Chunk("Mã NV : ", FontUtils.getDejavuBold10Dark()));
        employeeInfo.add(new Chunk(code + "\n", FontUtils.getDejavuRegular10Dark()));

        employeeInfo.add(new Chunk("Bộ phận: ", FontUtils.getDejavuBold10Dark()));
        employeeInfo.add(new Chunk(departmentName + "\n", FontUtils.getDejavuRegular10Dark()));

        employeeInfo.add(new Chunk("Chức vụ: ", FontUtils.getDejavuBold10Dark()));
        employeeInfo.add(new Chunk(positionName + "\n", FontUtils.getDejavuRegular10Dark()));

        // Tạo ô chứa thông tin
        PdfPCell infoCell = new PdfPCell(employeeInfo);
        infoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setPaddingBottom(10F); // khoảng cách với barcode
        barcodeTable.addCell(infoCell);
    }


    private void createBarcode(final PdfWriter writer, final PdfPTable barcodeTable, final Entity employee) {
        String number = employee.getStringField("code");

        Barcode128 code128 = new Barcode128();

        code128.setCode(number);

        PdfContentByte cb = writer.getDirectContent();

        Image barcodeImage = code128.createImageWithBarcode(cb, null, null);

        barcodeTable.addCell(barcodeImage);
    }

    private List<Entity> getEmployeesFromIds(final List<Long> ids) {
        return getEmployeeDD().find()
                .add(SearchRestrictions.in(L_ID, ids))
                .list().getEntities();
    }

    private DataDefinition getEmployeeDD() {
        return dataDefinitionService.get("rew52", "employeeDto");
    }

    @Override
    protected void addTitle(final Document document, final Locale locale) {
        document.addTitle(translationService.translate("rew52.employeeLabelsReport.report.title", locale));
    }

    @Override
    protected void setPageEvent(final PdfWriter writer) {
        writer.setPageEvent(new PdfPageNumbering(new Footer(), false, false));
    }
}