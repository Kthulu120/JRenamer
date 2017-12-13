package sample;

/**
 * Created by Troy on 12/9/2017.
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.io.FilenameUtils;

public class FileRenamer implements Runnable {
    Scanner scnr = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        FileRenamer r = new FileRenamer();
        r.run();

    }
    /**
     * TODO: Rename file depending based on if show or movie or album
     *
     *
     */
    @Override
    public void run() {
        // File (or directory) with old name
        String dir = getDirectory();
        /**
         * so we're trying to rename files to the name of their parent directory
         * But somehow we're having an OutOfBoundsException
         */
        File f = new File(dir);
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
        if(f.isDirectory()){
            for(int i = 0;i < files.size();i++){
                String path = files.get(i).getAbsolutePath();
                /**
                 * We create new paths for a new file so we can grab the new name to name the movie file
                 */
                System.out.println(path);
                File scenario = new File (path);
                String name = scenario.getName();

                long greatestSize = 0;
                int place = -1;
                if(scenario.isDirectory()){
                    List<File> childDir =  new ArrayList<File>(Arrays.asList(scenario.listFiles()));
                    for(int k = 0;k < childDir.size();k++ ){
                        if(greatestSize < childDir.get(k).length()){
                            greatestSize = childDir.get(k).length();
                            place  = k;
                        }
                    }
                    System.out.println(name + " " + childDir.get(place).getName());
                    File movieFile = new File(childDir.get(place).getAbsolutePath());
                    String ext1 = FilenameUtils.getExtension(childDir.get(place).getAbsolutePath());
                    File newFile = new File(path + "\\" +name+ "." + ext1 );
                    System.out.println(newFile.getAbsolutePath());
                    movieFile.renameTo(newFile);

                }

            }

        }

    }

    private void renameFiles(ArrayList<File> files,String directory){
        int count = files.size();
        for(int i = 0;i< count;i++){
            String ext1 = FilenameUtils.getExtension(files.get(i).getAbsolutePath());
            String file = files.get(i).getName().toString();
            String realExt = file.substring(file.length()- 3);
            int seasonNum= 1;
            int epiNum =1;
            // File (or directory) with new name
            File file3 = new File(directory + "\\s0" + seasonNum + "e0" + (i + epiNum ) + "." + realExt );
            boolean success = files.get(i).renameTo(file3);
            if (!success) {
                System.out.println("File was not successfully renamed");
            }
        }

    }


    private int getFirstEpisode() {
        System.out.println("What is the number of the first episode?");
        int episode = scnr.nextInt();
        return episode;
    }

    private String getDirectory() {
        System.out.println("What is the Directory?");
        String s = scnr.nextLine();
        return s;
    }

    private int getSeason(){
        int season = 0;
        System.out.println("What season is this?");
        season = scnr.nextInt();
        //System.out.println("What is the number of the first episode?");
        //episode = scnr.nextInt();
        return season;

    }

    public int determineType(){
        int type = -1;
        while(!(type >= 0 && type <=2)){
            System.out.println("Choose Type: \n 1:Movie \n 2:Show Series \n ");
            type = scnr.nextInt();
        }
        switch(type){
            case 0:
                //Directs Methods
                type = 0;
                break;
            case 1:
                type= 1;
                break;
            case(2):
                type = 2;
                break;
        }
        return type;
    }
    public static String toAbsolutePath(String maybeRelative) {
        Path path = Paths.get(maybeRelative);
        Path effectivePath = path;
        if (!path.isAbsolute()) {
            Path base = Paths.get("");
            effectivePath = base.resolve(path).toAbsolutePath();
        }
        return effectivePath.normalize().toString();
    }



    public ArrayList<File> convertListToAbs(ArrayList<File> files){
        ArrayList<File> newfiles = new ArrayList<File>();
        for(int i = 0;i < files.size();i++){
            File f = new File(files.get(i).getAbsolutePath());
        }
        return null;
    }



}

