package play.modules.shell;

import groovy.lang.Binding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.groovy.tools.shell.Groovysh;
import org.codehaus.groovy.tools.shell.IO;

import play.Play;
import play.db.jpa.JPAPlugin;

public class ShellRunner {

	public static void main(String[] args) throws IOException {
		File root = new File(System.getProperty("application.path"));
		Play.init(root, System.getProperty("play.id", ""));
		Play.start();

		IO io = new IO();
		Binding binding = new Binding();
		Groovysh shell = new Groovysh(Play.classloader, binding, io);

		shell.getImports().addAll(getImports());

		JPAPlugin pluginInstance = (JPAPlugin) Play.pluginCollection.getPluginInstance(JPAPlugin.class);
		pluginInstance.startTx(false);
		
		try {
			shell.run("");
		} finally {
			pluginInstance.closeTx(true);
		}
	}

	private static Collection getImports() {
		ArrayList<String> imports = new ArrayList<String>();
		imports.add("import models.*;");
		imports.add("import play.db.jpa.*;");
		return imports;
	}

}
