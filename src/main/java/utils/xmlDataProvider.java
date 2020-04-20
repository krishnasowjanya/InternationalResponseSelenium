package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class xmlDataProvider {
	
	
	public InputStream getXMLSourceByFund(String xmlURL) throws ClientProtocolException, IOException
	{		
		//Process http url and return the xml 	    
	    HttpParams httpParameters = new BasicHttpParams();	
	    HttpClient httpclient = new DefaultHttpClient(httpParameters);	    
	    //HttpGet httpget = new HttpGet("http://rcovlnx3076:9400/xml/product/ProductDetails?AppID=IW&Country=GB&Language=en_GB&FundID=18318");	
	    //HttpGet httpget = new HttpGet("http://rcovlnx3076:9400/xml/product/ProductPerformance?AppID=IW&Country=GB&Language=en_GB&FundID=2041&frequency=q");
	    
	    HttpGet httpget = new HttpGet(xmlURL);
	    HttpResponse response = httpclient.execute(httpget);	
	    InputStream in = response.getEntity().getContent();	    
	    return in;
	}
	
	public Element getXMLParentElementByTagName(InputStream inputXMLSource,String tagName,String tagTextValue) throws ParserConfigurationException, SAXException, IOException
	{     	 
		String sClass,nodeName[];
		Element parenetElm=null,shrClassElm;
		Node parentNode;
		
		//Xml parser
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();		
		Document doc = dBuilder.parse(new InputSource(inputXMLSource));
		doc.getDocumentElement().normalize();
		 
		 //Get the nodes based on given Tag Name
		 NodeList nodelist=doc.getElementsByTagName(tagName);		 
		 for(int j=0;j<nodelist.getLength();j++)
		 {
			 shrClassElm=(Element)nodelist.item(j);
			 //System.out.println("Share Class : "+ shrClassElm.getTextContent());
			 sClass=shrClassElm.getTextContent().toString();
			 
			 //Based on given Tag Text Value, find the corresponding parent node
			 if(sClass.equals(tagTextValue)) {
				 //System.out.println("Parent: " + shrClassElm.getParentNode().getNodeName());
	   			 parentNode=shrClassElm.getParentNode();
	   			 parenetElm=(Element)parentNode;	
	   			 break;
   			 }     		        			 	    	        			
		 
		 }
		 
		 return parenetElm;

	}
	
	public HashMap<String, String> getChildNodesWithTextValues(Element parentElement)
	{
		String nodeName[];
		HashMap<String, String> MatchingFundDetails = new HashMap<String, String>();
		
		if(!parentElement.hasChildNodes())
		{	
		 //collect all child-node items with name and value in collection array
		 for (int k = 0; k <parentElement.getChildNodes().getLength()-1 ; k++) 
		 {			
			 //System.out.println("Item: " + k + " " +  parentElement.getChildNodes().item(k).getTextContent()); 			
			 nodeName=parentElement.getChildNodes().item(k).getNodeName().toString().split(":");
			 //System.out.println("Child Node: " + nodeName[1] + " , Text value: " + parentElement.getChildNodes().item(k).getTextContent());
			 MatchingFundDetails.put(nodeName[1].trim(), parentElement.getChildNodes().item(k).getTextContent());		        				 
			
		 }
		} 
		 return MatchingFundDetails;
	}
	
	public static void main(String[] str) throws ParserConfigurationException, SAXException
	{
		xmlDataProvider xmlDP=new xmlDataProvider();
		InputStream input;
		Element parent;
		HashMap<String, String> fundInfo = new HashMap<String, String>();
		
		try {
			input=xmlDP.getXMLSourceByFund("http://rcovlnx3076:9400/xml/product/ProductDetails?AppID=IW&Country=GB&Language=en_GB&FundID=18318");
			parent=xmlDP.getXMLParentElementByTagName(input, "ns1:SHRCLS_NAME", "W (acc)");
			fundInfo=xmlDP.getChildNodesWithTextValues(parent);
			
			System.out.println("Fund ID: " + fundInfo.get("TA_ID"));
			
		} catch (ClientProtocolException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
