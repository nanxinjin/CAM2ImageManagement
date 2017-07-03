package com.jinnanxin;

import static java.lang.System.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.*;

//import javax.activation.MimetypesFileTypeMap;


public class Main {

    public static void main(String[] args) {
	/* write your code here */
        final long startTime = System.currentTimeMillis();
        File folder = new File("/Users/NanxinJ1/Documents/research/CAM2ImageManagement/ImageData");
        File[] listOfFiles = folder.listFiles();
        List<String[]> myForm = new ArrayList<String[]>();
        int index = 0;
//----------------TODO: Count file number in order to separate them in different directory
        String imageName = "", imageID = "", imageDate = "", imageTime = "", imageYear = "", imageMonth = "", imageDay = "", imageHour = "", imageMin = "";
        int yearStart=0, yearEnd=0;
//---------check whether all images go into their folders with their camera ID
        int noImageFlag = 0;
        for(int i = 0; i < listOfFiles.length; i++){
            //System.out.println("TEST: checking there is no image in folder.");
            //System.out.println("TEST: listOFfFiles["+i+"]: " + listOfFiles[i]);
            imageName = listOfFiles[i].getName();
            if(listOfFiles[i].isFile() && imageName.matches("(?i).*.png")) {
                noImageFlag = 1;
                break;
            }
        }

        if(noImageFlag == 1) {
            for (int i = 0; i < listOfFiles.length; i++) {
                imageName = listOfFiles[i].getName();
                if (listOfFiles[i].isFile() && imageName.matches("(?i).*.png")) {
                    System.out.println("imageName " + listOfFiles[i].getName());
//-------------Already got all image in a directory, should check whether there is a same ID folder exists.
                    Pattern patternDate = Pattern.compile("_(.*?)_");
                    Matcher matcherDate = patternDate.matcher(imageName);
//-------------Parse CameraID, Date, Time
                    if (matcherDate.find()) {
                        // System.out.println("imageDate " + matcherDate.group(1));
                        imageDate = matcherDate.group(1);
                        //-------Check the first '_' in filename
                        //System.out.println("start: " + matcherDate.start());
                        yearStart = matcherDate.start();
                        //-------Check the second '_' in filename
                        //System.out.println("end: " + matcherDate.end());
                        yearEnd = matcherDate.end();
                        imageID = imageName.substring(0, yearStart);
                        imageTime = imageName.substring(yearEnd, imageName.length() - 4);
                        System.out.println("imageID: " + imageID);
                        System.out.println("imageDate: " + imageDate);
                        System.out.println("imageTime: " + imageTime);
                        imageYear = imageDate.substring(0,4);
                        imageMonth = imageDate.substring(5,7);
                        imageDay = imageDate.substring(8,10);
                        imageHour = imageTime.substring(0,2);
                        imageMin = imageTime.substring(3);

/*
*
*ImageID    Year    Month    Day    Hour    MIN
*  0          1       2       3      4       5
*
*
*
*
*
*
* */
                        myForm.add(new String[]{imageID,imageYear,imageMonth,imageDay,imageHour,imageHour});
                        System.out.println("index: " + index);
                        System.out.println("imageID: " + myForm.get(0)[0]);
                        System.out.println("imageYear: " + myForm.get(0)[1]);
                        System.out.println("imageMonth: " + myForm.get(0)[2]);
                        System.out.println("imageDay: " + myForm.get(0)[3]);
                        System.out.println("imageHour: " + myForm.get(0)[4]);
                        System.out.println("imageMin: " + myForm.get(0)[5]);

                    }

                    //Check whether a CameraID directory exists or not
                    int checkDirectory = 0;
                    for (int j = 0; j < listOfFiles.length; j++) {
                        if (listOfFiles[j].isDirectory() && listOfFiles[j].getName().equals(imageID)) {
                            System.out.println("Directory " + imageID + " exist.");
                            checkDirectory = 1;
                            break;
                        }
                    }
//----------If directory already exists, then put the image into it; if not, then create a new directory.
                    //------Get and check current directory
                    String workingDir = System.getProperty("user.dir");
                    System.out.println("Current working directory : " + workingDir);



 //------------Directory does not exist
                    if (checkDirectory == 0) {

                        //File newDir = new File(imageID);
                        File newDirectory = new File("/Users/NanxinJ1/Documents/research/CAM2ImageManagement/ImageData/"+imageID);
                        newDirectory.mkdirs();
                        //File newDir = new File(imageID);
                        //newDir.mkdir();
                    }

                    //---------check current file's absolute path
                    System.out.println("File " + listOfFiles[i] + " on Path : " + listOfFiles[i].getAbsolutePath());


                    //TODO: You can change directories if you want.
                    String newPath = "/Users/NanxinJ1/Documents/research/CAM2ImageManagement/ImageData/"+imageID;
                    //newPath = new File(workingDir, imageID).getPath();
                    System.out.println("newPath: " + newPath);
                    String newFile = new File(newPath, imageName).getPath();
                    System.out.println("newFile: " + newFile);
                    //move file into new directory
                    listOfFiles[i].renameTo(new File(newFile));
                    checkDirectory = 0;


                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
                System.out.println("------------------------------------------------------");
            }
        }else{
//---------All images already went into their folders
            System.out.println("All images went into their folders by ImageID");
            //TODO: dealling with year for each imageID folder
            for (int j = 0; j < listOfFiles.length; j++) {
                if (listOfFiles[j].isDirectory()) {
                    System.out.println("Directory " + imageID + " exist.");
                    break;
                }
            }
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) );
    }
}
