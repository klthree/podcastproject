
/*
 * The podcastproject package contains classes that are part of a podcast client
 *
 * All code in main(String[] args) methods are for testing purposes only.
 *
 * Podcast represents a podcast, with, among other things, a List of Episodes.
 *
 */

package podcastproject;

import java.util.List;
import java.util.ArrayList;

public class Podcast extends Show {
    private String imageUrl;
    private String rssFeedUrl;

    public Podcast() {
    }

    public Podcast(String rssFeedUrl) {
        this.rssFeedUrl = rssFeedUrl;
    }

    public String getRssFeedUrl () {
        return rssFeedUrl;
    }

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
        return "\nShow title: " + getTitle() + "\nShow description: " + getDescription()/* + "\nShow link: " + getLink() + "\nLanguage: " + getLanguage() + "\n" + "Image found at: " + getImageUrl()*/;
    }
}
