import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.Package;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.PostConstruct;

public class ListOfFiles {
	
   public static void main(String args[]) throws IOException {
	        
	  //Creating a File object for directory
	  File directoryPath = new File("C:\\Users\\Elite8300\\Documents\\SampleApp"); 
      String donee = getFolderContents(directoryPath);
      getClassList();
	  System.out.println(donee + " ---");
   }
   
   public static void getClassList() throws IOException {
	   ArrayList<String> classNames = new ArrayList<String>();
	   String outFileName = "abc.txt";
	   String flname = "C:\\Users\\Elite8300\\Documents\\SampleApp\\WrittenFiles\\" + outFileName;
	   try {
		   FileInputStream targetStream = new FileInputStream(new File("C:\\Users\\Elite8300\\Documents\\STS4Workspace\\customer-service-app\\target\\customer-service-app-0.0.1-SNAPSHOT.jar"));   
		   ZipInputStream zip = new ZipInputStream(targetStream);
		   for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
		       if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
		           String className = entry.getName().replace('/', '.'); // including ".class"
		           classNames.add(className.substring(0, className.length() - ".class".length()));
		       }
		   }
	   } catch (IOException e) {
           e.printStackTrace();
       };
       FileWriter writer = new FileWriter(flname, true);
	   System.out.println("classNames.size:--- " + classNames.size() + " ---");
       for (String clName: classNames) {
    	   //System.out.println("classNames:--- " + clName);
    	   writer.write(clName + "\n");
       };
       writer.close();
   }
   
   public Set<String> findAllPackagesStartingWith(String prefix) {
	    List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
	    classLoadersList.add(ClasspathHelper.contextClassLoader());
	    classLoadersList.add(ClasspathHelper.staticClassLoader());
	    Reflections reflections = new Reflections(new ConfigurationBuilder()
	            .setScanners(new SubTypesScanner(false), new ResourcesScanner())
	            .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
	            .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("my.base.package"))));
	    Set<Class<? extends Object>> classes = reflections.getSubTypesOf(Object.class);

	    Set<String> packageNameSet = new TreeSet<String>();
	    for (Class classInstance : classes) {
	        String packageName = classInstance.getPackage().getName();
	        if (packageName.startsWith(prefix)) {
	            packageNameSet.add(packageName);
	        }
	    }
	    return packageNameSet;
	}
   
   public static void getClassMethodsList1(Object clss) throws IOException {
	   try {
           Class classobj = clss.getClass();
           Method[] methods = classobj.getMethods(); 
           for (Method method : methods) { 
               String MethodName = method.getName();
               System.out.println("Name of the method: " + MethodName);
           }
       }
       catch (Exception e) {
           e.printStackTrace();
       }
   }
   
   public static void getClassMethodsList2(Object ob) throws IOException {
	   try {
		   //Class c = ob.getClass();
		   for (Class<?> c = ob.getClass(); c != null; c = c.getSuperclass()) {
		     for (Method method : c.getDeclaredMethods()) {
		       if (method.getAnnotation(PostConstruct.class) != null) {
		         System.out.println(c.getName() + "." + method.getName());
		       }
		     }
		   }
       }
       catch (Exception e) {
           e.printStackTrace();
       }
   }
   
   public static String getFolderContents(File directoryPath) {
	   System.out.println("running getFolderContents...");
	   System.out.println("..........................");
	   //List of all files and directories 
	   File filesList[] = directoryPath.listFiles();
	   List<File> subDirectoryGroup = new ArrayList<File>();
	   
	   List<String> textFilesList = new ArrayList<String>();
	   List<String> xmlFilesList = new ArrayList<String>();
	   List<String> javaList = new ArrayList<String>();
	   List<String> jspList = new ArrayList<String>();
	   List<String> docList = new ArrayList<String>();
	   List<String> pdfList = new ArrayList<String>();
	   List<String> dpfList = new ArrayList<String>();	   
	   List<String> warList = new ArrayList<String>();
	   List<String> jarList = new ArrayList<String>();
	   List<String> earList = new ArrayList<String>();
	   List<String> propertiesFilesList = new ArrayList<String>();
	   List<String> subDirectoryList = new ArrayList<String>();
	   List<String> unknownFilesList = new ArrayList<String>();
	      
	   System.out.println("Specified directory: " + directoryPath);
	   System.out.println("---");
	   for(File file : filesList) {
	   if (file.getName().toLowerCase().endsWith(".txt")) {
		   textFilesList.add(file.getName());
	   } else if (file.getName().toLowerCase().endsWith(".xml")) {
		   xmlFilesList.add(file.getName());
	   } else if (file.getName().toLowerCase().endsWith(".properties")) {
		   propertiesFilesList.add(file.getName());
	   } else if (file.getName().toLowerCase().endsWith(".java")) {
	        	 javaList.add(file.getName());
	         } else if (file.getName().toLowerCase().endsWith(".jsp")) {
	        	 jspList.add(file.getName());
	         } else if (file.getName().toLowerCase().endsWith(".doc") || file.getName().toLowerCase().endsWith(".docx")) {
	        	 docList.add(file.getName());
	         } else if (file.getName().toLowerCase().endsWith(".pdf")) {
	        	 pdfList.add(file.getName());
	         } else if (file.getName().toLowerCase().endsWith(".dpf")) {
	        	 dpfList.add(file.getName());
	         } else if (file.getName().toLowerCase().endsWith(".war")) {
	        	 warList.add(file.getName());
	         } else if (file.getName().toLowerCase().endsWith(".jar")) {
	        	 jarList.add(file.getName());
	         } else if (file.getName().toLowerCase().endsWith(".ear")) {
	        	 earList.add(file.getName());
	         } else if (file.isDirectory()) {
	        	 subDirectoryList.add(file.getName());
	        	 subDirectoryGroup.add(file);
	        	 ObjTree objtree = new ObjTree();
	        	 objtree.setName(file.getName());
	         } else {
	        	 unknownFilesList.add(file.getName());
	         }
	    }
	   // Print out number of files found
	   System.out.println("List of files: " + filesList.length);
	   System.out.println(" ");
	      
	   System.out.println("running printTreeContents...");
	   System.out.println("..........................");
	   printTreeContents(textFilesList, ".txt");
	   printTreeContents(xmlFilesList, ".xml");
	   printTreeContents(propertiesFilesList, ".properties");
	   printTreeContents(javaList, ".java");
	   printTreeContents(jspList, ".jsp");
	   printTreeContents(docList, ".doc");
	   printTreeContents(pdfList, ".pdf");
	   printTreeContents(dpfList, ".dpf");
	   printTreeContents(warList, ".war");
	   printTreeContents(jarList, ".jar");
	   printTreeContents(earList, ".ear");
	   printTreeContents(subDirectoryList, ".dir");
	   printTreeContents(unknownFilesList, ".unknown");
	   System.out.println(" ");
	   
	   // now run the buildFolderTree logic
	   System.out.println("running buildFolderTree...");
	   System.out.println("..........................");
	   buildFolderTree(subDirectoryGroup, 1);
	   System.out.println(" ");
	   
	   return "DONE!";
   }
   
   public static void printTreeContents(List<String> val, String name) {
	   if (val.size() > 0) {
		// Print out Text files details only
		   System.out.println("# of " + name + " files: " + val.size());
		   for(String fileName : val) {
		      System.out.println("   - " + fileName);
		   }
		   System.out.println(" ");		   
	   }	   
   }
   
   public static void buildFolderTree(List<File> subDirectoryGroup, int level) {
	   List<FileTyp> txtList = new ArrayList<FileTyp>();
	   System.out.println("subDirectoryGroup Size: " + subDirectoryGroup.size());
	   
	   for(File file : subDirectoryGroup) {
		   FileTyp fileTyp = new FileTyp();
		   fileTyp.setName(file.getName());
		   fileTyp.setExtens(".txt");
		   fileTyp.setAbspath(file.getAbsolutePath());
		   fileTyp.setLevel(level + 1);

		   txtList.add(fileTyp);
		   System.out.println(fileTyp.getName() + ", " + fileTyp.getExtens() + ", " + fileTyp.getLevel() + ", " + fileTyp.getAbspath());
	   }
	   System.out.println(" ");
	   
	   if (subDirectoryGroup.size() > 0) {
		// now run the innerFolderTree0 logic
		   System.out.println("running innerFolderTree0...");
		   System.out.println("+++++++++++++++++++++++++++");
		   FileTyp fileTyp0 = new FileTyp();
		   fileTyp0.setAbspath(subDirectoryGroup.get(0).getAbsolutePath());
		   File dirFolderPath = new File(fileTyp0.getAbspath());
		   System.out.println("dirFolderPath: " + dirFolderPath.getName());
		   getFolderContents(dirFolderPath);		   
	   }	      
   }
}