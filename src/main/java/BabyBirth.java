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
                if (record.get(0).equals(name)) {
                    return rank;
                }
            }
        }
        return -1;
    }

    public void testGetRank(){
        String name = "Frank";
        String gender = "M";
        int year = 1971;
        int rank = getRank(year, name, gender);

        System.out.println("The rank of the name " + name + " is " + rank + " (the gender is " + gender + ", the year is " + year + ")");
    }

    public String getNameWithRank(int year, int rank, String gender) {
        FileResource fr = new FileResource("C:/Users/kpriluch/IdeaProjects/BabyNames/src/main/java/us_babynames/us_babynames_by_year/yob"+year+".csv");
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
        int rank = 450;
        int year = 1982;
        String gender = "M";

        String name = getNameWithRank(year, rank, gender);
        if (name.equals("NO NAME")){  System.out.println(name); }
        else {
            System.out.println("The name with the rank " + rank + " is " + name + " (the gender is " + gender + ")");
        }
    }

    public void whatIsNameInYear(Integer currentYear, Integer mayBeYear, String name, String gender) {
        int currentRank = getRank(currentYear, name, gender);
        String mayBeName= getNameWithRank(mayBeYear, currentRank, gender);

        System.out.println(name + " born in " + currentYear + " would be " + mayBeName + " if she was born in " + mayBeYear + ".");
    }

    public Integer yearOfHighestRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int currentMaxRankYear = -1;
        int currentMaxRank = -1;

        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser(false);
            int currentYear = Integer.parseInt(f.getName().substring(3,7));
            int tempRank = 0;
            int rank = -1;

            for (CSVRecord record: parser){
                if(record.get(1).equals(gender)){
                    tempRank += 1;
                    if(record.get(0).equals(name)){
                        rank = tempRank;
                        break;
                    }
                }
            }
            if (currentMaxRank == -1 && rank != -1){
                currentMaxRank = rank;
                currentMaxRankYear = currentYear;
            } else if(rank<currentMaxRank && rank != -1){
                currentMaxRank = rank;
                currentMaxRankYear = currentYear;
            }
        }
        return currentMaxRankYear;
    }

    private double getAverageRank(String name, String gender){
        double sumOfRank = 0.0;
        double numberOfYears = 0.0;
        double averageRank = -1.0;
        DirectoryResource dr = new DirectoryResource();

        for(File f: dr.selectedFiles()){
            numberOfYears += 1.0;
            FileResource fr = new FileResource(f);
            int year = Integer.parseInt(f.getName().substring(3,7));
            int rank = getRank(year, name, gender);

            if (rank != -1){ sumOfRank += (double)rank; }
        }

        if(sumOfRank != 0.0){ averageRank = sumOfRank/numberOfYears; }
        return averageRank;
    }

    public Integer getTotalBirthsRankedHigher(int year, String name, String gender) {
        FileResource fr = new FileResource("C:/Users/kpriluch/IdeaProjects/BabyNames/src/main/java/us_babynames/us_babynames_by_year/yob"+year+".csv");
        int totalBirthsRankedHigher = 0;
        int comparedRank = getRank(year, name, gender);

        for (CSVRecord record : fr.getCSVParser(false)) {
            int currentRank = getRank(year, record.get(0), gender);

            if (record.get(1).equals(gender) && currentRank < comparedRank) {
                totalBirthsRankedHigher += Integer.parseInt(record.get(2));
            }
        }
        return totalBirthsRankedHigher;
    }

    public static void main(String[] args) {
        BabyBirth o = new BabyBirth();
        //o.testTotalBirth();
        //o.testGetRank();
        //o.testGetNameWithRank();
        //o.whatIsNameInYear(1974, 2014, "Owen", "M");
        //o.whatIsNameInYear(2012, 2014, "Isabella", "F");
        //System.out.println(o.yearOfHighestRank("Mich","M"));
        //System.out.println(o.getAverageRank("Robert","M"));
        System.out.println(o.getTotalBirthsRankedHigher(1990, "Emily", "F"));
    }


}
