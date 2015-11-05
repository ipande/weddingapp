package desipride.socialshaadi.shadidata;

/**
 * Created by parth.mehta on 10/31/15.
 */
public class FamilyMember {
    private String name;
    private String relation;
    private String information;
    private int thumbnail;

    public FamilyMember(String name, String relation, String information, int thumbnail) {
        this.name = name;
        this.relation = relation;
        this.information = information;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
