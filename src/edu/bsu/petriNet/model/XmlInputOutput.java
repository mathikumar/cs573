package edu.bsu.petriNet.model;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

// TODO error handling http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
public class XmlInputOutput {

	public static PetriNet readModel(String sourcePath) {

		PetriNet p = new PetriNet();

		try {

			File fXmlFile = new File(sourcePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("place");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					int id = Integer.parseInt(eElement.getAttribute("id"));
					String name = eElement.getAttribute("name");
					int tokenN = Integer.parseInt(eElement.getAttribute("numberOfTokens"));
					int x = Integer.parseInt(eElement.getAttribute("x"));
					int y = Integer.parseInt(eElement.getAttribute("y"));
					
					p.createPlace(id, name, tokenN, x, y);
				}
			}

			nList = doc.getElementsByTagName("transition");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					int id = Integer.parseInt(eElement.getAttribute("id"));
					String name = eElement.getAttribute("name");
					int x = Integer.parseInt(eElement.getAttribute("x"));
					int y = Integer.parseInt(eElement.getAttribute("y"));


					p.createTransition(id, name, x, y);
				}
			}

			nList = doc.getElementsByTagName("arc");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					int id = Integer.parseInt(eElement.getAttribute("id"));
					String name = eElement.getAttribute("name");
					int sourceId = Integer.parseInt(eElement.getAttribute("source"));
					int targetId = Integer.parseInt(eElement.getAttribute("target"));
					int weight = Integer.parseInt(eElement.getAttribute("weight"));

					p.createArc(id, name, sourceId, targetId, weight);
				}
			}

			p.updateIdGenerator();

		} catch (Exception e) {
			e.printStackTrace();

		}

		return p;
	}

	public static Boolean printModel(PetriNet p, String destinationPath) {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("petrinet");
			doc.appendChild(rootElement);

			// places element
			Element places = doc.createElement("places");
			rootElement.appendChild(places);

			// add each place element
			for (Place pla : p.getPlaces()) {
				Element placeE = doc.createElement("place");
				placeE.setAttribute("id", String.valueOf(pla.getID()));
				placeE.setAttribute("name", pla.getName());
				placeE.setAttribute("numberOfTokens", String.valueOf(pla.getNumberOfTokens()));
				placeE.setAttribute("x", String.valueOf(pla.getX()));
				placeE.setAttribute("y", String.valueOf(pla.getY()));
				
				places.appendChild(placeE);
			}

			// transitions element
			Element transitions = doc.createElement("transitions");
			rootElement.appendChild(transitions);

			// add each transition element
			for (Transition tran : p.getTransitions()) {
				Element transitionE = doc.createElement("transition");
				transitionE.setAttribute("id", String.valueOf(tran.getID()));
				transitionE.setAttribute("name", tran.getName());
				transitionE.setAttribute("x", String.valueOf(tran.getX()));
				transitionE.setAttribute("y", String.valueOf(tran.getY()));
				

				transitions.appendChild(transitionE);
			}

			// arcs element
			Element arcs = doc.createElement("arcs");
			rootElement.appendChild(arcs);

			// add each place element
			for (Arc ar : p.getArcs()) {
				Element arcE = doc.createElement("arc");
				arcE.setAttribute("id", String.valueOf(ar.getID()));
				arcE.setAttribute("name", ar.getName());
				arcE.setAttribute("source", String.valueOf(ar.getSource().getID()));
				arcE.setAttribute("target", String.valueOf(ar.getTarget().getID()));
				arcE.setAttribute("weight", String.valueOf(ar.getWeight()));

				arcs.appendChild(arcE);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(destinationPath));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);


		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			return false;
		}

		return true;
	}

	public static void main(String argv[]) {
		PetriNet n = readModel("sample.xml");

		printModel(n, "out.xml");
		
		PetriNet n2 = readModel("out.xml");

		printModel(n2, "out2.xml");
		
		
		System.out.println("asd");
	}
}
