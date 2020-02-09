/******************************************************************************* 
 * Copyright (c) 2019 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package com.redhat.qute.lsp4e;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.lsp4e.server.ProcessStreamConnectionProvider;

/**
 * Qute language server.
 * 
 * @author Angelo ZERR
 *
 */
public class QuteLanguageServer extends ProcessStreamConnectionProvider {

	public QuteLanguageServer() {
		List<String> commands = new ArrayList<>();
		commands.add(computeJavaPath());
		commands.add("-classpath");
		try {
			URL url = FileLocator.toFileURL(getClass().getResource("/server/com.redhat.qute.ls-uber.jar"));
			commands.add(new java.io.File(url.getPath()).getAbsolutePath());
			commands.add("com.redhat.qute.ls.QuteServerLauncher");
			setCommands(commands);
			setWorkingDirectory(System.getProperty("user.dir"));
		} catch (IOException e) {
			QuteLSPPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,
					QuteLSPPlugin.getDefault().getBundle().getSymbolicName(), e.getMessage(), e));
		}
	}

	private String computeJavaPath() {
		String javaPath = "java";
		boolean existsInPath = Stream.of(System.getenv("PATH").split(Pattern.quote(File.pathSeparator))).map(Paths::get)
				.anyMatch(path -> Files.exists(path.resolve("java")));
		if (!existsInPath) {
			File f = new File(System.getProperty("java.home"),
					"bin/java" + (Platform.getOS().equals(Platform.OS_WIN32) ? ".exe" : ""));
			javaPath = f.getAbsolutePath();
		}
		return javaPath;
	}

	@Override
	public String toString() {
		return "Qute Language Server: " + super.toString();
	}

}