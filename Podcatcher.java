/*
 * The podcastproject package contains classes that are part of a podcast client
 *
 * All code in main(String[] args) methods are for testing purposes only.
 *
 * Podcatcher class is designed to parse a podcast-specced rss feed and return
 * relevant information in the form of Podcast and Episode objects.
 */

package podcastproject;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

import javax.xml.stream.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

public class Podcatcher {

    private static Scanner keyboard = new Scanner(System.in);

    // createShow takes the url of a podcast feed and parses it (StAX, cursor API) to get Podcast
    // specific information, including title, description, and link, and returns
    // a Podcast
    public Podcast createShow(String xmlUrl) {
        Podcast podcast = null;

        try {
            XMLInputFactory xmlif = XMLInputFactory.newInstance();

            XMLStreamReader xmlsr;
            
            podcast = new Podcast(xmlUrl);
            
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

    // Takes the name of a file that contains a list of podcast feed urls, delimited by newlines, and returns a List of those Podcast objects. Does so by using the createShow method.
    public List<Podcast> createShowListFromFile (String filename) {
        List<Podcast> showList = new ArrayList<>();
        
        try {
            FileReader fr = new FileReader("podcastproject/" + filename);
            BufferedReader br = new BufferedReader(fr);
            Podcatcher podcatcher = new Podcatcher();

            String nextline;

            while ((nextline = br.readLine()) != null) {
                showList.add(podcatcher.createShow(nextline));
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }

        
        return showList;
    }

    // Takes a Podcast, gets its feed url, and uses StAX to parse the xml to
    // create a List of Episodes
    public void createEpisodeList (Podcast podcast) {
        Episode episode = null;

        try {
            XMLInputFactory xmlif = XMLInputFactory.newInstance();

            XMLStreamReader xmlsr;
            
            String xmlUrl = podcast.getRssFeedUrl();
            
            xmlsr = xmlif.createXMLStreamReader(new URL(xmlUrl).openStream());
            
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
                        else if (xmlsr.getEventType() == XMLStreamConstants.START_ELEMENT &&
                                xmlsr.getNamespaceURI() == null) {
                            
                            String elementName = xmlsr.getLocalName();
                            
                            switch (elementName) {
              
                                case "enclosure":
                                    episode.setEnclosureUrl(xmlsr.getAttributeValue(0));
                                    break;
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
    }

    // Takes an XMLStreamReader to search a subset of xml file for image
    // information. In its own method to clean up code.
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

    // Testing
    public static void main (String[] args) {

//        System.out.print("Enter name of file: ");
//        String filename = keyboard.nextLine();
//
//        List<Podcast> pcList;
//
//        Podcatcher podcatcher = new Podcatcher();
//        pcList = podcatcher.createShowListFromFile(filename);
        
        int count = 0;

//        for (Podcast pc: pcList) {
//            System.out.println(count++ + "\n" + pc + "\n\n");
//        }
        
//        while (true) {
//
//            System.out.print("Enter show number to see episode list: ");
//            String selection = keyboard.nextLine();
//            
//            if (selection.equals("-1")) {
//                break;
//            }
//            
//            podcatcher.createEpisodeList(pcList.get(Integer.parseInt(selection)));
//
//            List<Episode> epList = pcList.get(Integer.parseInt(selection)).getEpisodeList();
//            int epListSize = epList.size();
//    
//            for (Episode ep: epList) {
//                System.out.println("Episode: " + epListSize);
//                System.out.println(ep);
//                System.out.println("------------------------------------------------------------------------------------------");
//            }
//        
//        }

        System.out.print("Enter feed url: ");
        String url = keyboard.nextLine();

        Podcatcher podcatcher = new Podcatcher();     
        Podcast practiceShow = podcatcher.createShow(url);
        
        System.out.println(practiceShow);
       
        podcatcher.createEpisodeList(practiceShow);


//           Collections.reverse(epList);

//           count = 1;
           for (Episode ep: practiceShow.getEpisodeList()) {
               System.out.println("Episode " + count++ + ":");
               System.out.println(ep);
           }
        //System.out.println(podcatcher.getEpisodeList());
    } 
}
