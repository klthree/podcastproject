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

public class Podcatcher {

    private List<Episode> episodeList;
    private Episode episode;
    
    public void staxTest (String xmlUrl) {
        Scanner keyboard = new Scanner(System.in);
        String prefix = "";
        if (!xmlUrl.startsWith("http") && !xmlUrl.startsWith("https")) {
            xmlUrl = "http://" + xmlUrl;
        }

        try {
            episodeList = new ArrayList<>();
            XMLInputFactory xmlif = XMLInputFactory.newInstance();

//            XMLStreamReader xmlr = xmlif.createXMLStreamReader(new FileReader(xmlFile));
            XMLStreamReader xmlr = xmlif.createXMLStreamReader(new URL(xmlUrl).openStream());
            Show show = new Show();
            
            while (xmlr.hasNext()) {
                if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT &&
                    xmlr.getLocalName().equals("channel")) {                        
                        
                        while (xmlr.hasNext()) {
                            if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT &&
                                xmlr.getLocalName().equals("item")) {
                                System.out.println(show.toString());
                                break;
                            }
                            else if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                                String element = xmlr.getLocalName();
                                xmlr.next();
                                if (xmlr.getEventType() == XMLStreamConstants.CHARACTERS) {
                                    String value = xmlr.getText();
                                    switch (element) {
                                        case "title":
                                            System.out.println(value);
                                            show.setTitle(value);
                                            break;
                                        case "description":
                                            show.setDescription(value);
                                            break;
                                        case "link":
                                            show.setLink(value);
                                            break;
                                        case "language":
                                            show.setLanguage(value);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                
                            }
                            xmlr.next();
                        }
                }
                else if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT &&
                    xmlr.getLocalName().equals("item")) {
                        episode = new Episode();
                         
                        while (xmlr.hasNext()) {
                            if (xmlr.getEventType() == XMLStreamConstants.END_ELEMENT && 
                                    xmlr.getLocalName().equals("item")) {
                                episodeList.add(episode);
                                System.out.println(episode.toString());
                                break;        
                            }
                            else if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                                String element = xmlr.getLocalName();
//                                if (xmlr.getEventType() == XMLStreamConstants.CHARACTERS) {
                                    switch (element) {
                                        case "title":
                                            xmlr.next();
                                            episode.setTitle(xmlr.getText());
                                            break;
                                        case "description":
                                            xmlr.next();
                                            episode.setDescription(xmlr.getText());
                                            break;
                                        case "link":
                                            xmlr.next();
                                            episode.setLink(xmlr.getText());
                                            break;
                                        case "language":
                                            xmlr.next();
                                            episode.setLanguage(xmlr.getText());
                                            break;
                                        case "guid":
                                            xmlr.next();
                                            episode.setGuid(xmlr.getText());
                                            break;
                                        case "pubDate":
                                            xmlr.next();
                                            episode.setPubDate(xmlr.getText());
                                            break;
                                        case "enclosure":
                                            episode.setUrl(xmlr.getAttributeValue(0));
                                        default:
                                            break;
                                    }
//                                }
                                
                            }
                            xmlr.next();
                        }
                }
                xmlr.next();
            }
            for (Episode ep: episodeList) {
                System.out.println(ep);
            }

            System.out.print("Enter guid of episode to download: ");
            String episodeToDownload = keyboard.nextLine();
            for (Episode ep: episodeList) {
                if (ep.getGuid().contains(episodeToDownload)) {
                    Downloader dler = new Downloader(ep.getUrl());
                    dler.download();
                    break;
                }
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

    public List<Show> getEpisodeList () {
        List<Show> copy = new ArrayList<>(episodeList);
        return copy;
    }

    public static void main (String[] args) {
        // Cursori
        //
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter feed url: ");
        String url = keyboard.nextLine();
//        staxTest("http://feed.thisamericanlife.org/talpodcast?format=xml");
        Podcatcher podcatcher = new Podcatcher();
        podcatcher.staxTest(url);
        System.out.println(podcatcher.getEpisodeList());
    }
}
