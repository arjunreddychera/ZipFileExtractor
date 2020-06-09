import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 */

/**
 * @author Arjun
 *
 */
public class ZipExtracterUtility {
	
	private static final int BUFFER_SIZE = 4096;
	private static final String filename="README.txt";

	public void unzip(String zipFileLocation, String destDirectory) throws IOException {
		
		File destDir = null;
		boolean isLocationPresent=true;
		boolean isReadMetxtFound=false;
		Path path=Paths.get(zipFileLocation);
		if(!Files.exists(path))
		{	System.out.println(zipFileLocation + "<-- does not exists.. Please check it");
		    isLocationPresent=false;
		}
		if(isLocationPresent)
		{
			
		String currentDir = System.getProperty("user.dir");
		destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
            System.out.println(destDir+" does not exists.. Creating it ");
        }
        else if(destDirectory==null)
        {
        	destDirectory=currentDir;
		destDir = new File(currentDir);   
        }	
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFileLocation));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            
            if (!entry.isDirectory() && (filePath.contains(filename) )) {
            	isReadMetxtFound=true;
            	extractFile(zipIn, filePath);
                ProcessBuilder pb=new ProcessBuilder("Notepad.exe",filePath);
                pb.start();
            } else {
                
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        
        if(!isReadMetxtFound)
			System.out.println("No file with name " + filename + " found in " + zipFileLocation +" directory " );
		} //end if
		
		
    }
	
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
  
	
	public static void main(String[] args) {
        //"D:/CCB-Material/C1.zip"
        //"D:/CCB-Matwerial1"
        String zipFilePath = args[1];
        String destDirectory = args[2];
        ZipExtracterUtility readmefinder = new ZipExtracterUtility();
        try {
            
        	readmefinder.unzip(zipFilePath, destDirectory);
        } catch (Exception ex) {
            // some errors occurred
            ex.printStackTrace();
        }
    }

}
