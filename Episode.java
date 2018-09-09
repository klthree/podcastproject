package podcastproject;

public class Episode {
    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String category;
    private String guid;
    private String duration;
    private String url;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    
    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }


    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String toString() {
        return "\nEpisode:\nguid: " + getGuid() + "\nEpisode title: " + getTitle() + "\nEpisode Description: " + getDescription() + "\nEpisode link: " + getLink() + "\nEpisode date: " + getPubDate() + "\nURL: " + getUrl() + "\n\n";
    }
}
