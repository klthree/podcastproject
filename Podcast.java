package podcastproject;

import java.util.List;
import java.util.ArrayList;

public class Podcast extends Show {
    private String imageUrl;
    
    private List<Episode> episodeList = new ArrayList<>();
    
    private boolean isDownloaded = false;

    public void setIsDownloaded(boolean isDownloaded) {
        this.isDownloaded = isDownloaded;
    }

    public boolean getIsDownloaded() {
        return this.isDownloaded;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void addEpisode(Episode e) {
        episodeList.add(e);
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public String toString() {
        return "\nShow title: " + getTitle() + "\nShow description: " + getDescription() + "\nShow link: " + getLink() + "\nLanguage: " + getLanguage() + "\n" + "Image found at: " + getImageUrl();
    }
}
