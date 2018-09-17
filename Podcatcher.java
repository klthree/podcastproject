package podcastproject;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import javax.xml.stream.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

public class Podcatcher {

//    private List<Episode> episodeList;
    private Episode episode;

    private Podcast podcast;

    private XMLInputFactory xmlif = XMLInputFactory.newInstance();

    private XMLStreamReader xmlsr;

    private static Scanner keyboard = new Scanner(System.in);

    public Podcast createShow(String xmlUrl) {
        try {
            podcast = new Podcast();
            xmlsr = xmlif.createXMLStreamReader(new URL(xmlUrl).openStream());

OUTER_LOOP: while(xmlsr.hasNext()) {
                
                if (xmlsr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    String elementName = xmlsr.getLocalName();
                    
                            switch (elementName) {
                                case "item":
                                    break OUTER_LOOP;
                                case "image":
                                    podcast.setImageUrl(getImageUrl(xmlsr));
                                    break OUTER_LOOP;
                                case "title":
                                    podcast.setTitle(xmlsr.getElementText());
                                    break;
                                case "description":
                                    podcast.setDescription(xmlsr.getElementText());
                                    break;
                                case "link":
                                    podcast.setLink(xmlsr.getElementText());
                                    break;
                                case "language":
                                    podcast.setLanguage(xmlsr.getElementText());
                                    break;
                                default:
                                    break;
                            }                    
                }
                
                xmlsr.next();
            }
        }
        catch (XMLStreamException e) {
            System.out.println(e);
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        catch (MalformedURLException e) {
                e.printStackTrace();
        }
        catch (IOException e) {
                e.printStackTrace();
        }
        
        return podcast;
    }

    public void createEpisodeList() {
        try {
            while (xmlsr.hasNext()) {
            
                if (xmlsr.getEventType() == XMLStreamConstants.START_ELEMENT &&
                        xmlsr.getLocalName().equals("item")) {
                    episode = new Episode();    
                    
                    while (xmlsr.hasNext()) {
                        
                        if (xmlsr.getEventType() == XMLStreamConstants.END_ELEMENT &&
                                xmlsr.getLocalName().equals("item")) {
                            podcast.addEpisode(episode);
                            break;
                        }
                        else if (xmlsr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                            String elementName = xmlsr.getLocalName();
                            
                                switch (elementName) {
                                    case "enclosure":
                                        episode.setEnclosureUrl(xmlsr.getAttributeValue(0));
                                    case "title":
                                        episode.setTitle(xmlsr.getElementText());
                                        break;
                                    case "description":
                                        episode.setDescription(xmlsr.getElementText());
                                        break;
                                    case "link":
                                        episode.setLink(xmlsr.getElementText());
                                        break;
                                    case "language":
                                        episode.setLanguage(xmlsr.getElementText());
                                        break;
                                    case "pubDate":
                                        episode.setPubDate(xmlsr.getElementText());
                                        break;
                                    case "guid":
                                        episode.setGuid(xmlsr.getElementText());
                                        break;

                                    default:
                                        break;
                                }
                        }
                        xmlsr.next();
                    }
                }
                xmlsr.next();                
            }
        }
        catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private String getImageUrl(XMLStreamReader xmlsr) {
        try {
            while (xmlsr.hasNext()) {
                
                if (xmlsr.getEventType() == XMLStreamConstants.START_ELEMENT &&
                        xmlsr.getLocalName().equals("url")) {
                        
                    xmlsr.next();
                    
                    if (xmlsr.getEventType() == XMLStreamConstants.CHARACTERS) {
                            return xmlsr.getText();
                    }
                }
            xmlsr.next();
            }
        }        
        catch (XMLStreamException e) {
            System.out.println(e);
        }

        return null;
    }

    public static void main (String[] args) {
        System.out.print("Enter feed url: ");
        String url = keyboard.nextLine();

        Podcatcher podcatcher = new Podcatcher();     
        Podcast practiceShow = podcatcher.createShow(url);
        
        System.out.println(practiceShow);
        
        podcatcher.createEpisodeList();

        List<Episode> epList = practiceShow.getEpisodeList();
        int count = epList.size();

        for (Episode ep: epList) {
            System.out.println("Episode: " + count--);
            System.out.println(ep);
            System.out.println("------------------------------------------------------------------------------------------");
        }

//            Collections.reverse(epList);
//
//            count = 1;
//            for (Episode ep: epList) {
//                System.out.println("Episode " + count++ + ":");
//                System.out.println(ep);
//            }
        //System.out.println(podcatcher.getEpisodeList());
    }
}
