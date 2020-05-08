
package Models;


public class UserModel {
    /*`USERID`,`NATIONALID`,`MOBILENUMBER`,`USERTYPE`,`USERNAME`,`PASSWORD`*/
    String userid,name,nationalid,mobilenumber,usertype,username,password;

    public UserModel(String userid, String name, String nationalid, String mobilenumber, String usertype, String username, String password) {
        this.userid = userid;
        this.name = name;
        this.nationalid = nationalid;
        this.mobilenumber = mobilenumber;
        this.usertype = usertype;
        this.username = username;
        this.password = password;
    }

    public UserModel(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalid() {
        return nationalid;
    }

    public void setNationalid(String nationalid) {
        this.nationalid = nationalid;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
