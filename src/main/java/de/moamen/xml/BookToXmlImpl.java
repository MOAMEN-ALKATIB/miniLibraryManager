package de.moamen.xml;

import de.moamen.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.Objects;

public class BookToXmlImpl implements BookToXml {
    private static final String XML_BASIS_DIR = new File("").getAbsoluteFile() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "BooksAsXml";
    private static final String XSD_FILE_PATH = new File("").getAbsoluteFile() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Book_schema.xsd";
    private static final Logger logger = LoggerFactory.getLogger(BookToXmlImpl.class);

    @Override
    public void saveBookAsXml(Book book) {
        try {
            logger.debug("Attempting to save book '{}' as XML to directory '{}'", book.getTitle(), XML_BASIS_DIR);            File dir = new File(XML_BASIS_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filePath = XML_BASIS_DIR + File.separator + book.getTitle() + ".xml";

            File[] listOfFiles = dir.listFiles(pathname -> pathname.getName().endsWith(".xml"));
            for (File file : Objects.requireNonNull(listOfFiles)) {
                if (file.getName().equalsIgnoreCase(book.getTitle() + ".xml")) {
                    logger.warn("Book '{}' already exists as XML at '{}'", book.getTitle(), file.getAbsolutePath());
                    return;
                }
            }
            JAXBContext context = JAXBContext.newInstance(Book.class);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(XSD_FILE_PATH));
            Marshaller marshaller = context.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(book, new File(filePath));
            logger.info("Book '{}' successfully saved as XML at {}", book.getTitle(), filePath);
        } catch (JAXBException | SAXException exception) {
            logger.error("Error saving book '{}' as XML", book.getTitle(), exception);        }
    }
}
