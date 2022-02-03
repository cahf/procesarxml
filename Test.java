
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import org.w3c.dom.NamedNodeMap;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

public class Test {
	static HashMap<String, HashMap<String, Object>> dataXml = new HashMap<String, HashMap<String, Object>>();

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        String xmlTest = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><retenciones:Retenciones  Version=\"1.0\"  FolioInt=\"1023538\"  FechaExp=\"2021-11-05T17:26:31-06:00\"  CveRetenc=\"16\"  DescRetenc=\"0.0\"  xmlns:retenciones=\"http://www.sat.gob.mx/esquemas/retencionpago/1\" xmlns:intereses=\"http://www.sat.gob.mx/esquemas/retencionpago/1/intereses\" xsi:schemaLocation=\"http://www.sat.gob.mx/esquemas/retencionpago/1 http://www.sat.gob.mx/esquemas/retencionpago/1/retencionpagov1.xsd http://www.sat.gob.mx/esquemas/retencionpago/1/intereses http://www.sat.gob.mx/esquemas/retencionpago/1/intereses/intereses.xsd\" Sello=\"SELLOTEMPORAL\" NumCert=\"NCERTIFICADOTEMPORAL\" Cert=\"CERTIFICADOTEMPOR AL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><retenciones:Emisor RFCEmisor=\"BSM970519DU8\" NomDenRazSocE=\"BANCO SANTANDER MEXICO S. A., INSTITUCION DE BANCA MULTIPLE, GRUPO FINANCIERO SANTANDER MEXICO\" /><retenciones:Receptor Nacionalidad=\"Nacional\" ><retenciones:Nacional RFCRecep=\"GOSJ380525AM1\" NomDenRazSocR=\"Prueba1\" /></retenciones:Receptor><retenciones:Periodo MesIni=\"1\" MesFin=\"12\" Ejerc=\"2020\" /><retenciones:Totales montoTotOperacion=\"29933661.00\" montoTotGrav=\"29933540.00\" montoTotExent=\"121.00\" montoTotRet=\"3120438.00\" ><retenciones:ImpRetenidos Impuesto=\"01\" montoRet=\"3120438.00\" TipoPagoRet=\"Pago provisional\" /></retenciones:Totales><retenciones:Complemento><intereses:Intereses Version=\"1.0\" SistFinanciero=\"SI\" RetiroAORESRetInt=\"NO\" OperFinancDerivad=\"NO\" MontIntNominal=\"29933661.00\" MontIntReal=\"0.00\" Perdida=\"-483093.00\" /></retenciones:Complemento><retenciones:Addenda xmlns:Santander=\"http://www.santander.com.mx/schemas/xsd/retencionSantander\"><Santander:retencionSantander><Santander:TipodeParticipacion>TITULAR</Santander:TipodeParticipacion><Santander:ResultadosdeFondosdeFondosInternacionales><Santander:ResultadoPorValuacion>0.0</Santander:ResultadoPorValuacion><Santander:ResultadoPorEnajenacion>0.0</Santander:ResultadoPorEnajenacion></Santander:ResultadosdeFondosdeFondosInternacionales><Santander:Otros><Santander:OtrosIngresos>0.0</Santander:OtrosIngresos><Santander:ISRetenido>0.0</Santander:ISRetenido></Santander:Otros></Santander:retencionSantander></retenciones:Addenda></retenciones:Retenciones>";

		Document doc = obtenerDocumento(xmlTest);
		JSONObject jo = new JSONObject(obtenerMapJson(doc));
	    System.out.println(jo.toString());
		

	}

	static Document obtenerDocumento(String xmlEntrada) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xmlEntrada));
		Document doc = builder.parse(is);
		return doc;

	}

	static HashMap<String, HashMap<String, Object>> obtenerMapJson(Document doc) {

		HashMap<String, HashMap<String, Object>> dataXml = new HashMap<String, HashMap<String, Object>>();
		HashMap<String, Object> cuerpos = new HashMap<String, Object>();
		HashMap<String, Object> intereses = new HashMap<String, Object>();

		try {

			DocumentTraversal trav = (DocumentTraversal) doc;

			// !MyFilter filter = new MyFilter();
			dataXml.put("emisor", new HashMap<String, Object>());
			dataXml.put("receptor", new HashMap<String, Object>());
			dataXml.put("encabezado", new HashMap<String, Object>());

			NodeIterator it = trav.createNodeIterator(doc.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null, true);

			for (Node node = it.nextNode(); node != null; node = it.nextNode()) {
				String name = node.getNodeName();
				if (name.equals("retenciones:Emisor")) {

					NamedNodeMap attrs = node.getAttributes();
					for (int i = 0; i < attrs.getLength(); i++) {
						Node attr = attrs.item(i);
						// TODO System.out.println(attr.getNodeName() + " : " +
						// attr.getTextContent().trim());
						HashMap<String, Object> aux = dataXml.get("emisor");
						String newName = "";
						if (attr.getNodeName().equals("NomDenRazSocE")) {
							newName = "nombre";
						} else {
							newName = "rfc";
						}
						aux.put(newName, attr.getTextContent().trim());
					}

				} else if (name.equals("retenciones:Receptor")) {

					NamedNodeMap attrs = node.getAttributes();
					for (int i = 0; i < attrs.getLength(); i++) {
						Node attr = attrs.item(i);
						HashMap<String, Object> aux = dataXml.get("receptor");
						String newName = "";

						if (attr.getNodeName().equals("Nacionalidad")) {
							newName = "nacionalidad";
						}
						aux.put(newName, attr.getTextContent().trim());
					}
				} else if (name.equals("retenciones:Nacional")) {

					NamedNodeMap attrs = node.getAttributes();
					HashMap<String, Object> aux = dataXml.get("receptor");
					for (int i = 0; i < attrs.getLength(); i++) {
						Node attr = attrs.item(i);
						String newName = "";
						if (attr.getNodeName().equals("RFCRecep")) {
							newName = "rfc";
						} else if (attr.getNodeName().equals("NomDenRazSocR")) {
							newName = "nombre";
						}
						aux.put(newName, attr.getTextContent().trim());
					}
				} else if (name.equals("retenciones:Periodo")) {

					HashMap<String, Object> aux = dataXml.get("encabezado");
					NamedNodeMap attrs = node.getAttributes();
					for (int i = 0; i < attrs.getLength(); i++) {
						Node attr = attrs.item(i);
						String newName = "";
						if (attr.getNodeName().equals("MesIni")) {
							newName = "mesIni";
						} else if (attr.getNodeName().equals("MesFin")) {
							newName = "mesFin";
						} else {
							newName = "ejerc";
						}
						aux.put(newName, attr.getTextContent().trim());
					}
				} else if (name.equals("retenciones:Totales")) {

					HashMap<String, Object> aux = dataXml.get("encabezado");
					NamedNodeMap attrs = node.getAttributes();
					for (int i = 0; i < attrs.getLength(); i++) {
						Node attr = attrs.item(i);
						aux.put(attr.getNodeName(), attr.getTextContent().trim());
					}
				} else if (name.equals("retenciones:ImpRetenidos")) {

					NamedNodeMap attrs = node.getAttributes();
					for (int i = 0; i < attrs.getLength(); i++) {
						Node attr = attrs.item(i);
						String newName = "";
						if (attr.getNodeName().equals("TipoPagoRet")) {
							newName = "tipoPagoRet";
						} else if (attr.getNodeName().equals("Impuesto")) {
							newName = "impuesto";
						} else {
							newName = attr.getNodeName();
						}
						cuerpos.put(newName, attr.getTextContent().trim());
					}
				} else if (name.equals("intereses:Intereses")) {
					NamedNodeMap attrs = node.getAttributes();
					for (int i = 0; i < attrs.getLength(); i++) {
						Node attr = attrs.item(i);
						String newName = "";
						if (attr.getNodeName().equals("SistFinanciero")) {
							newName = "sistFinanciero";
						} else if (attr.getNodeName().equals("RetiroAORESRetInt")) {
							newName = "retiroAORESRetInt";
						} else if (attr.getNodeName().equals("OperFinancDerivad")) {
							newName = "operFinancDerivad";
						} else if (attr.getNodeName().equals("MontIntNominal")) {
							newName = "montIntNominal";
						} else if (attr.getNodeName().equals("MontIntNominal")) {
							newName = "montIntReal";
						} else if (attr.getNodeName().equals("Perdida")) {
							newName = "perdida";
						} else {
							newName = attr.getNodeName();
						}
						intereses.put(newName, attr.getTextContent().trim());
					}
				}
			}

			System.out.println();

		} catch (Exception e) {

		}

		HashMap<String, Object> emisorAux = dataXml.get("receptor");
		emisorAux.put("email", "");

		HashMap<String, Object> encabezadoAux = dataXml.get("encabezado");
		encabezadoAux.put("tipoDocumento", "");
		encabezadoAux.put("procesoId", "");
		encabezadoAux.put("Referencia", "");
		encabezadoAux.put("cveRetenc", "");
		encabezadoAux.put("total", "");

		ArrayList<HashMap<String, Object>> arrCuerpos = new ArrayList<HashMap<String, Object>>();
		arrCuerpos.add(cuerpos);
		ArrayList<HashMap<String, Object>> arrIntereses = new ArrayList<HashMap<String, Object>>();
		arrIntereses.add(intereses);

		dataXml.get("encabezado").put("cuerpos", arrCuerpos);
		dataXml.get("encabezado").put("intereses", arrIntereses);

		return dataXml;
	}

	

}