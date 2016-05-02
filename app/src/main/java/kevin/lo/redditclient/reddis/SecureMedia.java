
package kevin.lo.redditclient.reddis;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class SecureMedia {

    @SerializedName("oembed")
    @Expose
    private Oembed oembed;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The oembed
     */
    public Oembed getOembed() {
        return oembed;
    }

    /**
     * 
     * @param oembed
     *     The oembed
     */
    public void setOembed(Oembed oembed) {
        this.oembed = oembed;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
