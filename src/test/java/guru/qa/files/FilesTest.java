package guru.qa.files;

import com.codeborne.pdftest.PDF;
import com.codeborne.pdftest.matchers.ContainsExactText;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.MatcherAssert.assertThat;

public class FilesTest {
    ClassLoader cl = FilesTest.class.getClassLoader();

    @Test
    void downloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File textFile = $("#raw-url").download();

        try (InputStream stream = Files.newInputStream(textFile.toPath())) {
            byte[] fileContent = IOUtils.toByteArray(stream);
            String strContent = new String(fileContent, StandardCharsets.UTF_8);
            org.assertj.core.api.Assertions.assertThat(strContent).contains("JUnit 5");
        }
    }

    @Test
    void pdfParsingTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("pdf/junit-user-guide-5.8.2.pdf")) {
            PDF pdf = new PDF(stream);
            Assertions.assertEquals(166, pdf.numberOfPages);
            assertThat(pdf, new ContainsExactText("JUnit 5"));
        }
    }

    @Test
    void xlsParsingTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("xlsx/sample-xlsx.xlsx")) {
            XLS xls = new XLS(stream);
            String cellValue = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
            org.assertj.core.api.Assertions.assertThat(cellValue).contains("Test text");
        }
    }

    @Test
    void csvParsingTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("csv/teachers.csv");
             CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            List<String[]> content = reader.readAll();
            org.assertj.core.api.Assertions.assertThat(content).contains(
                    new String[]{"Name", "Surname"},
                    new String[]{"Nikolay", "Postanogov"},
                    new String[]{"Andrey", "Ivanov"}
            );
        }
    }


    // Homework
    void pdfParsing(InputStream stream) throws Exception {
        PDF pdf = new PDF(stream);
        Assertions.assertEquals(166, pdf.numberOfPages);
        assertThat(pdf, new ContainsExactText("JUnit 5"));
    }

    void xlsParsing(InputStream stream) throws Exception {
        XLS xls = new XLS(stream);
        String cellValue = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
        org.assertj.core.api.Assertions.assertThat(cellValue).contains("Test text");
    }

    void csvParsing(InputStream stream) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            List<String[]> content = reader.readAll();
            org.assertj.core.api.Assertions.assertThat(content).contains(
                    new String[]{"Name", "Surname"},
                    new String[]{"Nikolay", "Postanogov"},
                    new String[]{"Andrey", "Ivanov"}
            );
        }
    }

    @Test
    void zipParsingTest() throws Exception {
        try (ZipFile zf = new ZipFile(new File("src/test/resources/zip/zipfile.zip"));
             ZipInputStream stream = new ZipInputStream(cl.getResourceAsStream("zip/zipfile.zip"))) {
            ZipEntry entry;
            while ((entry = stream.getNextEntry()) != null) {
                try (InputStream inputStream = zf.getInputStream(entry)) {
                    if (entry.getName().contains(".csv")) {
                        csvParsing(inputStream);
                    } else if (entry.getName().contains(".xlsx")) {
                        xlsParsing(inputStream);
                    } else if (entry.getName().contains(".pdf")) {
                        pdfParsing(inputStream);
                    }
                }
            }
        }
    }

}
