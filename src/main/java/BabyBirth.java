import edu.duke.*;
import org.apache.commons.csv.*;

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

    public Integer getRank(String name, String gender) {
        FileResource fr = new FileResource();
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

    public void testTotalBirth() {
        FileResource fr = new FileResource();
        ArrayList<Integer> totalBirth = TotalBirth(fr);
        System.out.println("TotalBirth = " + totalBirth.get(0) + ", total number of names = " + totalBirth.get(3));
        System.out.println("TotalBoy = " + totalBirth.get(1) + ", number of male names = " + totalBirth.get(4));
        System.out.println("TotalGirl = " + totalBirth.get(2) + ", number of female names = " + totalBirth.get(5));
    }

    public void testGetRank(){
        String name = "Noah";
        String gender = "M";
        int rank = getRank(name, gender);

        System.out.println("The rank of the name " + name + " is " + rank + " (the gender is " + gender + ")");

    }

    public static void main(String[] args) {
        BabyBirth o = new BabyBirth();
        //o.testTotalBirth();
        o.testGetRank();
    }
}
