/**
 * Copyright (c): 2017 Oleg Sklyar and contributors. License: MIT
 */
package nox;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.internal.plugins.ExtensionContainerInternal;
import org.gradle.api.internal.project.ProjectInternal;
import org.gradle.api.plugins.ExtraPropertiesExtension;

import nox.compilation.OSGiExt;
import nox.core.PlatformInfoHolder;
import nox.sys.Arch;
import nox.sys.OS;
import nox.sys.Win;


public class Platform implements Plugin<Project> {

	@Override
	public void apply(Project target) {
		ProjectInternal project = (ProjectInternal) target;

		ExtensionContainerInternal rootExt = project.getRootProject().getExtensions();
		if (rootExt.findByType(PlatformInfoHolder.class) == null) {
			rootExt.create(PlatformInfoHolder.name, PlatformInfoHolder.class);
		}

		ExtensionContainerInternal ext = project.getExtensions();
		ext.create(OSGiExt.name, OSGiExt.class, project, rootExt.findByType(PlatformInfoHolder.class));

		ExtraPropertiesExtension extProps = ext.getExtraProperties();
		// apply to every sub-project
		extProps.set("p2os", OS.current().toString());
		extProps.set("p2ws", Win.current().toString());
		extProps.set("p2arch", Arch.current().toString());
	}
}
