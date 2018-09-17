package podcastproject;

public class Episode extends Show {

    private String pubDate;
    
    private String category;
    
    private String guid;
    
    private String duration;
    
    private String enclosureUrl;


    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setEnclosureUrl(String enclosureUrl) {
        this.enclosureUrl = enclosureUrl;
    }

    public String getEnclosureUrl() {
        return enclosureUrl;
    }

    public String toString() {
        return "\nguid: " + getGuid() + "\nEpisode title: " + getTitle() + "\nEpisode Description: " + getDescription() + "\nEpisode link: " + getLink() + "\nEpisode date: " + getPubDate() + "\nDownload link: " + getEnclosureUrl() + "\n\n";
    }
}
