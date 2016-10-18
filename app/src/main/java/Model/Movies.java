package Model;

/**
 * Movies model with get fields setter and getter
 * colums name with source api
 */
public class Movies {
    public static final String sourceApi = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/";
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBTITLE = "subtitle";
    public static final String COLUMN_THUMB = "thumb";
    public static final String COLUMN_IMAGE = "img";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_STUDIO = "studio";
    public static final String COLUMN_SOURCE = "source";

    public final String fields[] = new String[]{ COLUMN_SUBTITLE, COLUMN_THUMB, COLUMN_IMAGE, COLUMN_TITLE,COLUMN_STUDIO,COLUMN_SOURCE};
    public String[] getValues() {
        return new String[]{ "" + subtitle, "" + thumb, "" + image, "" + title, "" + studio, "" + sourceUrl};

    }

    private String subtitle;
    private String sourceUrl;
    private String thumb;
    private String image;
    private String title;
    private String studio;
    private int id ;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getThumb() {
        return sourceApi+thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage() {
        return sourceApi+image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
