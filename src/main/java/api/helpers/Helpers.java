package api.helpers;

import api.exceptions.UnknowAttachment;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class Helpers {

	public static Environment environment;

	@Autowired
	public Helpers(Environment environment) {
		Helpers.environment = environment;
	}

	
	static public LocalDate toLocalDate(Date date) {
		
		if( date instanceof java.sql.Date )
			return ((java.sql.Date) date).toLocalDate();
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	static public boolean isEqual(Date d1, Date d2) {
		return toLocalDate(d1).isEqual(toLocalDate(d2));
	}
	
	
	static public boolean equals2(Date d1, Date d2) {
		
		long millisInADay = 1000 * 60 * 60 * 24;
		long m1 = d1.getTime() / millisInADay;
		long m2 = d2.getTime() / millisInADay;

		return m1 == m2;
	}
	
	
	static public void createFile(MultipartFile mpFile, String path) throws IOException {
		
		Path fp = Paths.get(path);
		
		//	Creating missing directories !!
		Files.createDirectories(fp.getParent());

		OutputStream OUT = Files.newOutputStream(fp);
		IOUtils.copy( mpFile.getInputStream() , OUT ); 
		OUT.close();
		
	}
	
	static public String getExtension(String fileName) {
		
		String[] fileNameSplits = fileName.split("\\.");
		int extensionIndex = fileNameSplits.length - 1;

		return fileNameSplits[extensionIndex];
	}
	

	static public List<String> getDirFilesName(String dir) throws IOException {

//		NoSuchFileException
//		return Files.walk(path).filter(Files::isRegularFile)
//					.map( file -> file.getFileName().toString())
//					.collect(Collectors.toList());

		try (Stream<Path> paths = Files.walk(Paths.get(dir))) {
			
			return paths
					.filter(Files::isRegularFile)
					.map( file -> file.getFileName().toString())
					.collect(Collectors.toList());
		} catch(NoSuchFileException e) {
			return Collections.emptyList();
		}
		
	}
	
	
	static public void renameFile(String fileToRename, String newName) throws IOException {
		
		Path fileToRenamePath = Paths.get(fileToRename);

		Files.move(
				fileToRenamePath, 
				fileToRenamePath.resolveSibling(newName),
				StandardCopyOption.REPLACE_EXISTING
		);
			    
	}
	
	static public String namingAttachFiles(String savingPath, Date namingDate, Integer idMarche, String fileName, String prefix, int index, boolean isImg) {
		
		return savingPath
				+ "/"
				+ (isImg ? "IMG-" : "DOC-")
				+ prefix + "" + formatDate(namingDate) 
				+ "-" + String.format("%03d", index) 
				+ "." + Helpers.getExtension(fileName);
	}
	
	static public String getOsPath(Integer idMarche) {
		return getAttachmentsPath() + "/" + idMarche + "/OS";
	}
	
	static public String getOsPathDate(Integer idMarche, Date d) {
		return getOsPath(idMarche) + "/" + formatDate(d);
	}
	
	static public String getDecPath(Integer idMarche) {
		return getAttachmentsPath() + "/" + idMarche + "/DEC";
	}
	
	static public String getDecPathDate(Integer idMarche, Date d) {
		return getDecPath(idMarche) + "/" + formatDate(d);
	}
	
	static public String getPathDate(Integer idMarche, String date, String type) {
		
		String path = getOsPath(idMarche);
		
		if(type.equals("OS")) path =  getOsPath(idMarche);
		else if(type.equals("DEC")) path =  getDecPath(idMarche);
		else throw new UnknowAttachment();
		
		return path + "/" + date;
	}
	
	static public String getAttachmentsPath() {
		return environment.getProperty("attachments.path");
	}
	
	static public String formatDate(Date d) {
		return new SimpleDateFormat("dd-MM-yyyy").format(d);
	}
	
	static public void deleteDir(String dir) throws IOException {
		FileSystemUtils.deleteRecursively((Paths.get(dir).toFile()));
//		FileUtils.deleteDirectory(Paths.get(dir).toFile());
	}
	
	
//	static public void checkProjetEditSecurity(Integer currentUser, Integer chargeSuiviID) {
//
//		if( ! RoleEnum.accessEditProject() && ! chargeSuiviID.equals(currentUser) ) {
//			System.out.println("@ForbiddenException");
//			throw new ForbiddenException("Vous n'avez pas le droit de modifier ce projet");
//		}
//	}

	
//	static public boolean canUserAssign(UserSession userSession) {
//		
//		
//		if( userSession.userType.equals(api.enums.UserType.administrateur.type()) ||
//			Stream.of(UserRole.controler_projet, UserRole.valider_projet, UserRole.affecter_projet).anyMatch(
//						authRole -> userSession.roles.stream().anyMatch(userRole -> userRole.equals(authRole.role)))
//		) { return true; }
//		
//		return false;
//	}


	public static <T, U> boolean compareLists(Collection<T> list1, Collection<U> list2, BiPredicate<T, U> predicate) {

		if( list1 == null || list2 == null )
			throw new IllegalArgumentException("null values not accepted");

		if( (!(list1 instanceof List) && !(list1 instanceof Set)) || (!(list1 instanceof List) && !(list2 instanceof Set)) )
			throw new IllegalArgumentException("Only List & Set are accepted");

		return list1.size() == list2.size() &&
				list1.stream().allMatch(itme1 -> list2.stream().anyMatch(item2 -> predicate.test(itme1, item2)));
	}

}
