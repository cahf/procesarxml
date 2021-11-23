

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;
import java.io.StringReader;



public class Test {


    public static void main(String[] args){

        try{
            String xmlTest = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmlEntrada><controlret>140|6|0|2020|F-0000500064|1023538|0009279542|I|2021-11-05T17:26:31|1</controlret><retencion>1.0|1023538|2021-11-05T17:26:31-06:00|16|</retencion><emisor>BSM970519DU8|BANCO SANTANDER MEXICO S. A., INSTITUCION DE BANCA MULTIPLE, GRUPO FINANCIERO SANTANDER MEXICO |</emisor><receptor>NACIONAL</receptor><nacional>GOSJ380525AM1|Prueba1|</nacional><extranjero>|</extranjero><periodo>1|12|2020</periodo><totales>29933661.00|29933540.00|121.00|3120438.00</totales><impretenidos>|01|3120438.00|PAGO PROVISIONAL</impretenidos><addenda>TITULAR|0.0|0.0|0.0|0.0</addenda><intereses>1.0|SI|NO|NO|29933661.00|0.00|-483093.00</intereses></xmlEntrada>";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlTest));
            Document doc = builder.parse(is);

            Element docElement = doc.getDocumentElement();
            NodeList childrenList = docElement.getChildNodes();
            System.out.println("lista de hijos:" + childrenList.getLength());
            for(int i = 0; i < childrenList.getLength(); i++){
                Node nodo = childrenList.item(i);
                if(nodo instanceof Element){
                    System.out.println("nombre de nodo:" + nodo.getNodeName());
                    System.out.println("contenido de nodo:" + nodo.getTextContent());

                }

            }














        }catch(Exception e){

        }



            
            

            
    }

  

}

