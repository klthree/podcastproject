package podcastproject;

public class Show {
    private String title;
    private String link;
    private String description;
    private String language;
    private List<Episode> = new ArrayList<>();

    public Show() {
    }

    public Show(String title, String description, String link, String language) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLanguage(String language) { 
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    

    public String toString() {
        return "\nShow title: " + title + "\nShow description: " + description + "\nShow link: " + link + "\nLanguage: " + language + "\n";
    }
}
