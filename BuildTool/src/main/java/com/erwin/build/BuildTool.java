package com.erwin.build;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class BuildTool {

	private static File file;

	private static String EMM_BASE_PATH = "D:\\WorkArea\\temp\\DG_MappingManager-erwinDGv11.0GA";

	private static File EMM_DEST_LIB_DIR = new File(EMM_BASE_PATH + File.separator + "build\\web\\WEB-INF\\lib");

	private static String dependentProjectBasePath[] = { "D:\\WorkSpace\\Eclipse\\TestJavaProject" };

	public void processDependetProject() {
		// TODO Auto-generated method stub

		System.out.println("---------- Processing Dependent Projects ----------");

		BuildScript buildScript = new BuildScript();

		for (String basePath : dependentProjectBasePath) {

			String projectName = basePath.substring(basePath.lastIndexOf(File.separator) + 1);

			System.out.println("Processing Project - " + projectName);

			buildScript.setProjectName(projectName);

			try {
				System.out.println("Generating Build Script for " + projectName);

				FileUtils.writeStringToFile(new File(basePath + "\\" + "build.xml"), buildScript.getScript());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			executeAntTask(basePath + "\\build.xml", null);

			System.out.println("\n\n");
		}
	}

	public void copyDependentJars() {
		// TODO Auto-generated method stub

		System.out.println("---------- Copying Dependent Jars To EMM ----------");

		for (String srcFile : dependentProjectBasePath) {

			try {

				File distPath = new File(srcFile + File.separator + "dist");

				System.out.println("Src File " + srcFile);

				FileUtils.copyDirectory(distPath, EMM_DEST_LIB_DIR);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		System.out.println("\n");
	}

	public void buildEmm() {
		// TODO Auto-generated method stub

		System.out.println("---------- Processing EMM ----------");

		executeAntTask(EMM_BASE_PATH + "\\build.xml", null);

	}

	public void startExecution(String DISuiteBasePath, String dependentProjectsBasePath[]) {
		// TODO Auto-generated method stub

		this.EMM_BASE_PATH = DISuiteBasePath;
		this.dependentProjectBasePath = dependentProjectsBasePath;

		processDependetProject();
		copyDependentJars();
		buildEmm();
	}

	public static void main(String[] args) {

		BuildTool start = new BuildTool();
		start.startExecution(EMM_BASE_PATH, dependentProjectBasePath);
		
		/*
		 * 
		 * System.out.println("---------- Processing Dependent Projects ----------");
		 * 
		 * BuildScript buildScript = new BuildScript();
		 * 
		 * for (String basePath : dependentProjectBasePath) {
		 * 
		 * String projectName = basePath.substring(basePath.lastIndexOf(File.separator)
		 * + 1);
		 * 
		 * System.out.println("Processing Project - " + projectName);
		 * 
		 * buildScript.setProjectName(projectName);
		 * 
		 * try { FileUtils.writeStringToFile(new File(basePath + "\\" + "build.xml"),
		 * buildScript.getScript()); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 * 
		 * executeAntTask(basePath + "\\build.xml", null);
		 * 
		 * System.out.println("\n\n"); }
		 * 
		 */

//		String libPropertiesFilePath = EMM_BASE_PATH + File.separator + "nbproject" + File.separator
//				+ "project.properties";
//		
//		Properties properties = new Properties();
//
//		System.err.println(libPropertiesFilePath);
//		
//		file = new File(libPropertiesFilePath);
//		
//		loadPropertiesFile(properties);
//		
//		try {
//			
//			FileInputStream inputStream = new FileInputStream(libPropertiesFilePath);
//			
//			properties.load(inputStream);
//			System.err.println(properties);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

//		for (String jarPath : dependentProjectBasePath) {
//
//			File distPath = new File(jarPath+File.separator+"dist");
//
//			File distJarsArray[] = distPath.listFiles(new FilenameFilter() {
//
//				public boolean accept(File dir, String name) {
//					// TODO Auto-generated method stub
//					return name.toLowerCase().endsWith(".jar");
//				}
//			});

		/*
		 * 
		 * System.out.println("---------- Copying Dependent Jars To EMM ----------");
		 * 
		 * for (String srcFile : dependentProjectBasePath) {
		 * 
		 * try {
		 * 
		 * File distPath = new File(srcFile + File.separator + "dist");
		 * 
		 * System.out.println("Src File " + srcFile);
		 * 
		 * FileUtils.copyDirectory(distPath, EMM_DEST_LIB_DIR); //
		 * properties.setProperty("file.reference."+srcFile.getName(),
		 * "lib/"+srcFile.getName()); // // OutputStream outputStream = new
		 * FileOutputStream(libPropertiesFilePath); // // properties.store(outputStream,
		 * "");
		 * 
		 * } catch (Exception e) { // TODO: handle exception e.printStackTrace(); } }
		 * 
		 * System.out.println("\n");
		 */

		/*
		 * System.out.println("---------- Processing EMM ----------");
		 * 
		 * executeAntTask(EMM_BASE_PATH + "\\build.xml", null);
		 */

	}

	public static boolean executeAntTask(String buildXmlFileFullPath, String target) {

		boolean success = false;
		DefaultLogger consoleLogger = getConsoleLogger();

		Project project = new Project();

		File buildFile = new File(buildXmlFileFullPath);
		project.setUserProperty("ant.file", buildFile.getAbsolutePath());
		project.addBuildListener(consoleLogger);

		try {

			project.fireBuildStarted();
			project.init();
			ProjectHelper projectHelper = ProjectHelper.getProjectHelper();
			project.addReference("ant.projectHelper", projectHelper);
			projectHelper.parse(project, buildFile);

			// If no target specified then default target will be executed.
			String targetToExecute = (target != null && target.trim().length() > 0) ? target.trim()
					: project.getDefaultTarget();

			project.executeTarget(targetToExecute);
			project.fireBuildFinished(null);

			success = true;

		} catch (BuildException buildException) {
			project.fireBuildFinished(buildException);
			throw new RuntimeException("!!! Unable to restart the App !!!", buildException);
		}

		return success;
	}

	private static DefaultLogger getConsoleLogger() {

		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);

		return consoleLogger;
	}
}