
package Models;


public class HalagatModel {
    String halagatid,halagatname,teachername,level;

    public HalagatModel(String halagatid, String halagatname, String teachername, String level) {
        this.halagatid = halagatid;
        this.halagatname = halagatname;
        this.teachername = teachername;
        this.level = level;
    }

    public String getHalagatid() {
        return halagatid;
    }

    public void setHalagatid(String halagatid) {
        this.halagatid = halagatid;
    }

    public String getHalagatname() {
        return halagatname;
    }

    public void setHalagatname(String halagatname) {
        this.halagatname = halagatname;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    
}
