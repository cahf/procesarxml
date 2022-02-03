

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
import org.w3c.dom.NamedNodeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



public class Test {
    static HashMap <String,HashMap<String,Object>>  dataXml = new HashMap<String,HashMap<String,Object>>();
    
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{


        String xmlTest = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><retenciones:Retenciones  Version=\"1.0\"  FolioInt=\"1023538\"  FechaExp=\"2021-11-05T17:26:31-06:00\"  CveRetenc=\"16\"  DescRetenc=\"0.0\"  xmlns:retenciones=\"http://www.sat.gob.mx/esquemas/retencionpago/1\" xmlns:intereses=\"http://www.sat.gob.mx/esquemas/retencionpago/1/intereses\" xsi:schemaLocation=\"http://www.sat.gob.mx/esquemas/retencionpago/1 http://www.sat.gob.mx/esquemas/retencionpago/1/retencionpagov1.xsd http://www.sat.gob.mx/esquemas/retencionpago/1/intereses http://www.sat.gob.mx/esquemas/retencionpago/1/intereses/intereses.xsd\" Sello=\"SELLOTEMPORAL\" NumCert=\"NCERTIFICADOTEMPORAL\" Cert=\"CERTIFICADOTEMPOR AL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><retenciones:Emisor RFCEmisor=\"BSM970519DU8\" NomDenRazSocE=\"BANCO SANTANDER MEXICO S. A., INSTITUCION DE BANCA MULTIPLE, GRUPO FINANCIERO SANTANDER MEXICO\" /><retenciones:Receptor Nacionalidad=\"Nacional\" ><retenciones:Nacional RFCRecep=\"GOSJ380525AM1\" NomDenRazSocR=\"Prueba1\" /></retenciones:Receptor><retenciones:Periodo MesIni=\"1\" MesFin=\"12\" Ejerc=\"2020\" /><retenciones:Totales montoTotOperacion=\"29933661.00\" montoTotGrav=\"29933540.00\" montoTotExent=\"121.00\" montoTotRet=\"3120438.00\" ><retenciones:ImpRetenidos Impuesto=\"01\" montoRet=\"3120438.00\" TipoPagoRet=\"Pago provisional\" /></retenciones:Totales><retenciones:Complemento><intereses:Intereses Version=\"1.0\" SistFinanciero=\"SI\" RetiroAORESRetInt=\"NO\" OperFinancDerivad=\"NO\" MontIntNominal=\"29933661.00\" MontIntReal=\"0.00\" Perdida=\"-483093.00\" /></retenciones:Complemento><retenciones:Addenda xmlns:Santander=\"http://www.santander.com.mx/schemas/xsd/retencionSantander\"><Santander:retencionSantander><Santander:TipodeParticipacion>TITULAR</Santander:TipodeParticipacion><Santander:ResultadosdeFondosdeFondosInternacionales><Santander:ResultadoPorValuacion>0.0</Santander:ResultadoPorValuacion><Santander:ResultadoPorEnajenacion>0.0</Santander:ResultadoPorEnajenacion></Santander:ResultadosdeFondosdeFondosInternacionales><Santander:Otros><Santander:OtrosIngresos>0.0</Santander:OtrosIngresos><Santander:ISRetenido>0.0</Santander:ISRetenido></Santander:Otros></Santander:retencionSantander></retenciones:Addenda></retenciones:Retenciones>";
        
        Document doc = obtenerDocumento(xmlTest);
        HashMap <String,HashMap<String,Object>> map = obtenerMapJson(doc);
        imprimirMapJson(map);
         


            
    }


    static Document obtenerDocumento(String xmlEntrada) throws ParserConfigurationException, SAXException, IOException{

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlEntrada));
        Document doc = builder.parse(is);
        return doc;

    }

    static HashMap <String,HashMap<String,Object>> obtenerMapJson(Document doc){

            HashMap <String,HashMap<String,Object>>  dataXml = new HashMap<String,HashMap<String,Object>>();
            HashMap <String,Object>  cuerpos = new HashMap<String,Object>();
            HashMap <String,Object>  intereses = new HashMap<String,Object>();

            try{
            
               
                DocumentTraversal trav = (DocumentTraversal) doc;
                
                //!MyFilter filter = new MyFilter();
                dataXml.put("emisor", new HashMap<String, Object>());
                dataXml.put("receptor", new HashMap<String, Object>());
                dataXml.put("encabezado", new HashMap<String, Object>());
    
                NodeIterator it = trav.createNodeIterator(doc.getDocumentElement(),NodeFilter.SHOW_ELEMENT,null,true);
    
                for (Node node = it.nextNode(); node != null; node = it.nextNode()) {
                    String name = node.getNodeName();
                    if (name.equals("retenciones:Emisor")) {
    
    
                        NamedNodeMap attrs = node.getAttributes();
                        for (int i = 0; i < attrs.getLength(); i++) {
                            Node attr = attrs.item(i);
                            // TODO System.out.println(attr.getNodeName() + " : " +
                            // attr.getTextContent().trim());
                            HashMap<String, Object> aux = dataXml.get("emisor");
                            aux.put(attr.getNodeName(), attr.getTextContent().trim());
                        }
    
                    } else if (name.equals("retenciones:Receptor")) {
    
                        NamedNodeMap attrs = node.getAttributes();
                        for (int i = 0; i < attrs.getLength(); i++) {
                            Node attr = attrs.item(i);
                            HashMap<String, Object> aux = dataXml.get("receptor");
                            aux.put(attr.getNodeName(), attr.getTextContent().trim());
                        }
                    }else if(name.equals("retenciones:Nacional")){
                        
                        NamedNodeMap attrs = node.getAttributes();
                        HashMap<String, Object> aux = dataXml.get("receptor");
                        for (int i = 0; i < attrs.getLength(); i++) {
                            Node attr = attrs.item(i);
                            aux.put(attr.getNodeName(), attr.getTextContent().trim());
                        }
                    }else if(name.equals("retenciones:Periodo")){
                        
                        HashMap<String, Object> aux = dataXml.get("encabezado");
                        NamedNodeMap attrs = node.getAttributes();
                        for (int i = 0; i < attrs.getLength(); i++) {
                            Node attr = attrs.item(i);
                            aux.put(attr.getNodeName(), attr.getTextContent().trim());
                        }
                    }else if(name.equals("retenciones:Totales")){
                        
                        HashMap<String, Object> aux = dataXml.get("encabezado");
                        NamedNodeMap attrs = node.getAttributes();
                        for (int i = 0; i < attrs.getLength(); i++) {
                            Node attr = attrs.item(i);
                            aux.put(attr.getNodeName(), attr.getTextContent().trim());
                        }
                    }else if(name.equals("retenciones:ImpRetenidos")){
                        
                        
                        NamedNodeMap attrs = node.getAttributes();
                        for (int i = 0; i < attrs.getLength(); i++) {
                            Node attr = attrs.item(i);
                            cuerpos.put(attr.getNodeName(), attr.getTextContent().trim());
                        }
                    }else if(name.equals("intereses:Intereses")){
                        
                        
                        NamedNodeMap attrs = node.getAttributes();
                        for (int i = 0; i < attrs.getLength(); i++) {
                            Node attr = attrs.item(i);
                            intereses.put(attr.getNodeName(), attr.getTextContent().trim());
                        }
                    }
                }
                    
                }catch(Exception e){
    
            }
            
        dataXml.get("encabezado").put("cuerpos", cuerpos);
        dataXml.get("encabezado").put("intereses", intereses);
        
        return dataXml;
    }

    static void imprimirMapJson(HashMap <String,HashMap<String,Object>> map){
                        
        
              
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            System.out.println("Key:" + key);
            HashMap<String, Object> auxMap = map.get(key);
            Iterator it2 = auxMap.keySet().iterator();
            while (it2.hasNext()) {

                String key2 = (String) it2.next();
                System.out.println(key2 + ": " + auxMap.get(key2));

            }
            System.out.println();
    }
   


    }


}

