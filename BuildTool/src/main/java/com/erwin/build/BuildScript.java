package com.erwin.build;

public class BuildScript {

	private StringBuilder builder = new StringBuilder();
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	private String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n";
	private String project = "<project  name=\" ";
	private String projectName;
	private String script = "\" basedir=\".\" default=\"main\">\r\n"
			+ "\r\n"
			+ "	<property name=\"src.dir\" value=\"src\" />\r\n"
			+ "	<property name=\"build.dir\" value=\"build\" />\r\n"
			+ "	<property name=\"classes.dir\" value=\"${build.dir}/classes\" />\r\n"
			+ "	<property name=\"dist.dir\" value=\"dist\" />\r\n"
			+ "	<property name=\"main-class\" value=\"Test\" />\r\n"
			+ "	<property name=\"lib.dir\" value=\"lib\" />\r\n"
			+ "\r\n"
			+ "	<path id=\"jars\">\r\n"
			+ "		<fileset dir=\"lib\" includes=\"**/*.jar\" />\r\n"
			+ "	</path>\r\n"
			+ "	\r\n"
			+ "	<!--<path id=\"dependentJars\" description=\"Dependent Jars Location\">\r\n"
			+ "		<fileset dir=\"${dist.dir}/${ant.project.name}-dependencies\" includes=\"**/*.jar\" />\r\n"
			+ "	</path>-->\r\n"
			+ "	\r\n"
			+ "	<manifestclasspath property=\"manifest.classpath\" jarfile=\"${dist.dir}\">\r\n"
			+ "		<classpath refid=\"jars\" />\r\n"
			+ "	</manifestclasspath>\r\n"
			+ "\r\n"
			+ "	<!--\r\n"
			+ "	<target name=\"zip-dependencies\">\r\n"
			+ "\r\n"
			+ "		<mkdir dir=\"${dist.dir}\" />\r\n"
			+ "\r\n"
			+ "		<jar jarfile=\"${dist.dir}/dependencies.jar\">\r\n"
			+ "			<zipgroupfileset dir=\"${lib.dir}\">\r\n"
			+ "				<include name=\"**/*.jar\" />\r\n"
			+ "			</zipgroupfileset>\r\n"
			+ "		</jar>\r\n"
			+ "	</target>\r\n"
			+ "\r\n"
			+ "	<target name=\"all\" depends=\"zip-dependencies\">\r\n"
			+ "\r\n"
			+ "		<jar jarfile=\"${dist.dir}/Tools.jar\">\r\n"
			+ "			<zipgroupfileset dir=\"${dist.dir}\">\r\n"
			+ "				<include name=\"**/*.jar\" />\r\n"
			+ "			</zipgroupfileset>\r\n"
			+ "		</jar>\r\n"
			+ "\r\n"
			+ "	</target>\r\n"
			+ "	\r\n"
			+ "	-->\r\n"
			+ "\r\n"
			+ "	<target name=\"clean\">\r\n"
			+ "		<delete dir=\"${build.dir}\" />\r\n"
			+ "		<delete dir=\"${dist.dir}\" />\r\n"
			+ "	</target>\r\n"
			+ "\r\n"
			+ "	<target name=\"compile\">\r\n"
			+ "		<mkdir dir=\"${build.dir}\" />\r\n"
			+ "		<javac srcdir=\"${src.dir}\" includeantruntime=\"true\" source=\"1.8\" destdir=\"${build.dir}\" includes=\"**/*.java\" target=\"1.8\">\r\n"
			+ "			<classpath refid=\"jars\" />\r\n"
			+ "		</javac>\r\n"
			+ "	</target>\r\n"
			+ "\r\n"
			+ "	<target name=\"jar\" depends=\"compile\">\r\n"
			+ "		<mkdir dir=\"${dist.dir}\" />\r\n"
			+ "		<jar destfile=\"${dist.dir}/${ant.project.name}.jar\" basedir=\"${build.dir}\">\r\n"
			+ "			<manifest>\r\n"
			+ "				<!--<attribute name=\"Main-Class\" value=\"${main-class}\"/>-->\r\n"
			+ "				<attribute name=\"Class-Path\" value=\"${manifest.classpath}\" />\r\n"
			+ "			</manifest>\r\n"
			+ "		</jar>\r\n"
			+ "	</target>\r\n"
			+ "\r\n"
			+ "	<target name=\"copy-file\">\r\n"
			+ "\r\n"
			+ "		<copy todir=\"${dist.dir}/${ant.project.name}-dependencies\" overwrite=\"true\">\r\n"
			+ "			<fileset dir=\"${lib.dir}\" includes=\"**/*.jar\" />\r\n"
			+ "		</copy>\r\n"
			+ "	</target>\r\n"
			+ "	\r\n"
			+ "	<target name=\"run\" depends=\"jar\">\r\n"
			+ "		<java jar=\"${dist.dir}/${ant.project.name}.jar\" fork=\"true\" />\r\n"
			+ "	</target>\r\n"
			+ "\r\n"
			+ "	<target name=\"clean-build\" depends=\"clean,jar\" />\r\n"
			+ "\r\n"
			+ "	<target name=\"main\" depends=\"clean,copy-file,run\" />\r\n"
			+ "\r\n"
			+ "</project>";
	
	public String getScript() {
		
		builder.append(head);
		builder.append(project);
		builder.append(projectName);
		builder.append(script);
		
		return builder.toString();
	}
	
}
