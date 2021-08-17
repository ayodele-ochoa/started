package Models;

import java.util.ArrayList;

public class RecyclerList
{
        private String albumTitle;
        private String thumbnailURL;

        public String getAlbumTitle()
        {
            return albumTitle;
        }

        public void setAlbumTitle(String title) {
            this.albumTitle = title;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }

        public void setImageUrl(String url) {
            this.thumbnailURL = url;
        }


}
