package com.jinnanxin;

import static java.lang.System.*;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//import javax.activation.MimetypesFileTypeMap;


public class Main {

    public static void main(String[] args) {
	/* write your code here */
        final long startTime = System.currentTimeMillis();
        File folder = new File("/Users/NanxinJ1/Documents/research/CAM2ImageManagement/ImageData");
        File[] listOfFiles = folder.listFiles();
//----------------TODO: Count file number in order to separate them in different directory
        String imageName = "", imageID = "", imageDate = "", imageTime = "";
        int yearStart=0, yearEnd=0;

        for (int i = 0; i < listOfFiles.length; i++) {
            imageName = listOfFiles[i].getName();
            if (listOfFiles[i].isFile() && imageName.matches("(?i).*.png")) {
                System.out.println("imageName " + listOfFiles[i].getName());
//-------------Already got all image in a directory, should check whether there is a same ID folder exists.
                Pattern patternDate = Pattern.compile("_(.*?)_");
                Matcher matcherDate = patternDate.matcher(imageName);
//-------------Parse CameraID, Date, Time
                if(matcherDate.find()){
                   // System.out.println("imageDate " + matcherDate.group(1));
                    imageDate = matcherDate.group(1);
                    //-------Check the first '_' in filename
                    //System.out.println("start: " + matcherDate.start());
                    yearStart = matcherDate.start();
                    //-------Check the second '_' in filename
                    //System.out.println("end: " + matcherDate.end());
                    yearEnd = matcherDate.end();
                    imageID = imageName.substring(0,yearStart);
                    imageTime = imageName.substring(yearEnd,imageName.length()-4);
                    System.out.println("imageID: " + imageID);
                    System.out.println("imageDate: " + imageDate);
                    System.out.println("imageTime: " + imageTime);

                }

                //Check whether a CameraID directory exists or not
                int checkDirectory = 0;
                for (int j = 0; j < listOfFiles.length; j++) {
                    if(listOfFiles[j].isDirectory() && listOfFiles[j].getName().equals(imageID)){
                        System.out.println("Directory " + imageID + " exist.");
                        checkDirectory = 1;
                        break;
                    }
                }
//----------If directory already exists, then put the image into it; if not, then create a new directory.
                //------Get and check current directory
                String workingDir = System.getProperty("user.dir");
                System.out.println("Current working directory : " + workingDir);

                String newPath = "";
                if(checkDirectory == 0){
                    //Directory does not exist
                    File newDir = new File(imageID);
                    newDir.mkdir();
                }

                //---------check current file's absolute path
                System.out.println("File " + listOfFiles[i] + " on Path : " + listOfFiles[i].getAbsolutePath());


                //TODO: You can change directories if you want.
                newPath =  new File(workingDir,imageID).getPath();
                System.out.println("newPath: " + newPath);
                String newFile = new File(newPath,imageName).getPath();
                System.out.println("newFile: " + newFile);
                //move file into new directory
                listOfFiles[i].renameTo(new File(newFile));
                checkDirectory = 0;


            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
            System.out.println("------------------------------------------------------");
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) );
    }
}
