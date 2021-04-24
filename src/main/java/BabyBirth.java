import edu.duke.*;
import org.apache.commons.csv.*;

import java.util.ArrayList;

public class BabyBirth {
    public void Printnames() {
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

        for (CSVRecord record : fr.getCSVParser(false)){
            numBorn = Integer.parseInt(record.get(2));
            totalBirth += numBorn;
            if (record.get(1).equals("M")) { totalBoy += numBorn; }
            else { totalGirl += numBorn; }
        }

        Birth.add(0, totalBirth);
        Birth.add(1, totalBoy);
        Birth.add(2, totalGirl);

        return Birth;
    }

    public void testTotalBirth() {
        FileResource fr = new FileResource();
        ArrayList<Integer> totalBirth = TotalBirth(fr);
        System.out.println("TotalBirth = " + totalBirth.get(0));
        System.out.println("TotalBoy = " + totalBirth.get(1));
        System.out.println("TotalGirl = " + totalBirth.get(2));
    }

    public static void main(String[] args) {
        BabyBirth o = new BabyBirth();
        o.testTotalBirth();
    }
}
