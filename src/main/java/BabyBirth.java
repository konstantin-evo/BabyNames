import edu.duke.*;
import org.apache.commons.csv.*;

import java.io.File;
import java.util.ArrayList;

public class BabyBirth {

    public void Printings() {
        FileResource fr = new FileResource();
        String gender;
        int numBorn;

        for (CSVRecord record : fr.getCSVParser(false)) { // CSV file does not have a header row
            numBorn = Integer.parseInt(record.get(2));

            if (record.get(1).equals("F")) { gender = "Female"; }
            else { gender = "Male"; }

            if (numBorn<=100) {
                System.out.println("Name is " + record.get(0)
                        + ", " + gender
                        + ", Num born is " + numBorn);
            }
        }
    }

    public ArrayList<Integer> TotalBirth(FileResource fr) {
        ArrayList<Integer> Birth = new ArrayList<>();
        int numBorn;
        int totalBirth = 0;
        int totalBoy = 0;
        int totalGirl = 0;
        int numGirlsName = 0;
        int numBoysName = 0;
        int numTotalName;

        for (CSVRecord record : fr.getCSVParser(false)){
            numBorn = Integer.parseInt(record.get(2));
            totalBirth += numBorn;
            if (record.get(1).equals("M")) {
                totalBoy += numBorn;
                numBoysName += 1;
            }
            else {
                totalGirl += numBorn;
                numGirlsName += 1;}
        }

        numTotalName = numBoysName + numGirlsName;

        Birth.add(0, totalBirth);
        Birth.add(1, totalBoy);
        Birth.add(2, totalGirl);
        Birth.add(3, numTotalName);
        Birth.add(4, numBoysName);
        Birth.add(5, numGirlsName);

        return Birth;
    }

    public void testTotalBirth() {
        FileResource fr = new FileResource();
        ArrayList<Integer> totalBirth = TotalBirth(fr);
        System.out.println("TotalBirth = " + totalBirth.get(0) + ", total number of names = " + totalBirth.get(3));
        System.out.println("TotalBoy = " + totalBirth.get(1) + ", number of male names = " + totalBirth.get(4));
        System.out.println("TotalGirl = " + totalBirth.get(2) + ", number of female names = " + totalBirth.get(5));
    }

    public Integer getRank(Integer year, String name, String gender) {
        FileResource fr = new FileResource("C:/Users/kpriluch/IdeaProjects/BabyNames/src/main/java/us_babynames/us_babynames_by_year/yob"+year+".csv");
        int rank = 0;

        for (CSVRecord record : fr.getCSVParser(false)) {

            if (record.get(1).equals(gender)) {
                rank += 1;
                if (record.get(0).equals(name) ) {
                    return rank;
                }
            }
        }
        return -1;
    }

    public void testGetRank(){
        String name = "James";
        String gender = "M";
        int year = 1880;
        int rank = getRank(year, name, gender);

        System.out.println("The rank of the name " + name + " is " + rank + " (the gender is " + gender + ", the year is " + year + ")");
    }

    public String getNameWithRank(int year, int rank, String gender) {
        FileResource fr = new FileResource("C:/Users/kpriluch/IdeaProjects/BabyNames/src/main/java/us_babynames/us_babynames_by_year/yob"+year+".csv");
        String name = null;
        int currentRank = 0;

        for (CSVRecord record : fr.getCSVParser(false)) {
            if (record.get(1).equals(gender)) {
                currentRank += 1;
                if (currentRank == rank) {
                    return record.get(0);
                }
            }
        }
        return "NO NAME";
    }

    public void testGetNameWithRank(){
        int rank = 3;
        int year = 1880;
        String gender = "F";

        String name = getNameWithRank(year, rank, gender);
        if (name.equals("NO NAME")){  System.out.println(name); }
        else {
            System.out.println("The name with the rank " + rank + " is " + name + " (the gender is " + gender + ")");
        }
    }

//    public void whatIsNameInYear(Integer currentYear, Integer mayBeYear, String name, String gender) {
//        int currentRank = getRank(name, gender);
//        int mayBeRank = getRank(name, gender);
//        File myFile = new File("C://Users/kpriluch/IdeaProjects/BabyNames/src/main/java/us_babynames/us_babynames_by_year/yob1881.csv");
//
//        System.out.println(myFile.getName());
//        System.out.println(name + " born in " + currentYear + " would be" + "Sophia if she was born in 2014.");
//
//    }

    public static void main(String[] args) {
        BabyBirth o = new BabyBirth();
        //o.testTotalBirth();
        //o.testGetRank();
        o.testGetNameWithRank();
        //o.whatIsNameInYear(1992, 1993, "Olivua", "F");
    }
}
