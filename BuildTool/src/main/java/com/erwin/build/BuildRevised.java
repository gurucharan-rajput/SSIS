package com.erwin.build;

import java.io.File;

import org.apache.tools.ant.Location;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

public class BuildRevised {

private static File file;
	
	private static String EMM_BASE_PATH = "D:\\WorkArea\\temp\\DG_MappingManager-erwinDGv11.0GA";
	
	private static File EMM_DEST_LIB_DIR = new File(EMM_BASE_PATH + File.separator + "build\\web\\WEB-INF\\lib");

	private static String dependentProjectBasePath[] = { "D:\\WorkSpace\\Eclipse\\TestJavaProject" };
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("---------- Processing Dependent Projects ----------");

		for (String basePath : dependentProjectBasePath) {

			String projectName = basePath.substring(basePath.lastIndexOf(File.separator) + 1);
			
			System.out.println("Processing Project - " + projectName);

			Project project = new Project();
			
			File file = new File(basePath);
			
			project.setBaseDir(file);
			project.setDefault("main");
			project.setName(projectName);
			
			project.setProperty("src.dir", "src");
			project.setProperty("build.dir", "build");
			project.setProperty("classes.dir", project.getProperty("build.dir")+file.separator+"classes");
			project.setProperty("dist.dir", "dist");
			project.setProperty("main-class", "");
			project.setProperty("lib.dir", "lib");
			
			Target target = new Target();
			target.setName("clean");
			target.setProject(project);
			
			Location location = new Location("build.xml");
			
			
			Task task = project.createTask("delete");
			task.setTaskName("clean");

			
			project.addTarget(target);
			project.fireBuildStarted();
			
			System.out.println("\n\n");
		}
		
	}

}
