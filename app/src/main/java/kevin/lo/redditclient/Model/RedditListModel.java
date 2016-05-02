package kevin.lo.redditclient.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class RedditListModel implements Parcelable {

    private String title;

    private String author;

    private String date;

    private int numberOfComments;

    private String thumbnailUrl;

    private String previewSourceUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public RedditListModel() {
    }

    public String getPreviewSourceUrl() {
        return previewSourceUrl;
    }

    public void setPreviewSourceUrl(String previewSourceUrl) {
        this.previewSourceUrl = previewSourceUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.date);
        dest.writeInt(this.numberOfComments);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.previewSourceUrl);
    }

    protected RedditListModel(Parcel in) {
        this.title = in.readString();
        this.author = in.readString();
        this.date = in.readString();
        this.numberOfComments = in.readInt();
        this.thumbnailUrl = in.readString();
        this.previewSourceUrl = in.readString();
    }

    public static final Creator<RedditListModel> CREATOR = new Creator<RedditListModel>() {
        @Override
        public RedditListModel createFromParcel(Parcel source) {
            return new RedditListModel(source);
        }

        @Override
        public RedditListModel[] newArray(int size) {
            return new RedditListModel[size];
        }
    };
}
