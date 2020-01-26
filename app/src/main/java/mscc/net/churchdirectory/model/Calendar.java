package mscc.net.churchdirectory.model;

public class Calendar {

    public String monthId;
    public String monthName;
    public String monthTimestamp;


    public Calendar(String id, String name, String timestamp) {
        monthId = id;
        monthName = name;
        monthTimestamp = timestamp;
    }
    public Calendar() {
    }

    public void setMonthId(String id){
        this.monthId=id;
    }
    public void setMonthName(String name){
        this.monthName=name;
    }
    public void setMonthTimestamp(String timestamp){
        this.monthTimestamp = timestamp;
    }


    public String getId() {
        return monthId;
    }
    public String getMonthName(){
        return monthName;
    }

    public String getMonthTimestamp() {
        return monthTimestamp;
    }
}
