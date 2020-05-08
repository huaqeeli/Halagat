package Models;

public class StudentModel {

    String studentid, name, age, birthdate, nationality, nationalid, halagahaname,issuePlace,mobileNumber,level;

    public StudentModel(String studentid, String name, String age, String birthdate, String nationality, String nationalid) {
        this.studentid = studentid;
        this.name = name;
        this.age = age;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.nationalid = nationalid;
    }

    public StudentModel(String studentid, String name, String halagahaname) {
        this.studentid = studentid;
        this.name = name;
        this.halagahaname = halagahaname;
    }

    public StudentModel(String studentid, String name, String age, String birthdate, String nationality, String nationalid, String halagahaname, String issuePlace, String mobileNumber, String level) {
        this.studentid = studentid;
        this.name = name;
        this.age = age;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.nationalid = nationalid;
        this.halagahaname = halagahaname;
        this.issuePlace = issuePlace;
        this.mobileNumber = mobileNumber;
        this.level = level;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHalagahaname() {
        return halagahaname;
    }

    public void setHalagahaname(String halagahaname) {
        this.halagahaname = halagahaname;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationalid() {
        return nationalid;
    }

    public void setNationalid(String nationalid) {
        this.nationalid = nationalid;
    }

}
