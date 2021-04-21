package ir.ac.kntu;

public class Date {

    private int day;

    private int month;

    private int year;

    public Date(int day, int month, int year) {
        if (checkDate(day, month, year)){
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    public Date(String strDate){
        String[] splitDate = strDate.split("/");
        for (int i=0;i<splitDate.length;i++){
            splitDate[i] = splitDate[i].strip();
        }
        int day = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[2]);

        new Date(day, month, year);
    }

    private boolean checkDate(int day, int month, int year){
        return day <= 31 && month <= 12 && day >= 1 && month >= 1 && year >= 1380 && (month <= 6 || day != 31);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }
}
