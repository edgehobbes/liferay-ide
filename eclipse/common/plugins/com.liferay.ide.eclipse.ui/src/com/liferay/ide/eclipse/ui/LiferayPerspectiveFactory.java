/*******************************************************************************
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *******************************************************************************/

package com.liferay.ide.eclipse.ui;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.views.IViewDescriptor;

/**
 * @author Greg Amerson
 */
public class LiferayPerspectiveFactory implements IPerspectiveFactory {

	public static final String ID = "com.liferay.ide.eclipse.ui.perspective.liferay";

	protected static final String ID_CONSOLE_VIEW = "org.eclipse.ui.console.ConsoleView"; //$NON-NLS-1$
	
	protected static final String ID_DATA_VIEW = "org.eclipse.datatools.connectivity.DataSourceExplorerNavigator"; //$NON-NLS-1$

	protected static String ID_J2EE_HIERARCHY_VIEW = "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

	protected static final String ID_MARKERS_VIEW = "org.eclipse.ui.views.AllMarkersView";

	protected static String ID_NEW_HOOK_WIZARD = "com.liferay.ide.eclipse.portlet.ui.wizard.hook";
	
	protected static String ID_NEW_PLUGIN_PROJECT_WIZARD = "com.liferay.ide.eclipse.project.ui.newProjectWizard";

	protected static String ID_NEW_PORTLET_WIZARD = "com.liferay.ide.eclipse.portlet.ui.wizard.portlet";

	protected static String ID_NEW_SERVICE_BUILDER_WIZARD = "com.liferay.ide.eclipse.portlet.ui.wizard.servicebuilder";

	protected static String ID_PACKAGE_EXPLORER_VIEW = "org.eclipse.jdt.ui.PackageExplorer";

	protected static final String ID_SEARCH_VIEW = "org.eclipse.search.ui.views.SearchView"; //$NON-NLS-1$

	protected static String ID_SERVERS_VIEW = "org.eclipse.wst.server.ui.ServersView"; //$NON-NLS-1$

	protected static final String ID_TASKLIST_VIEW = "org.eclipse.mylyn.tasks.ui.views.tasks";
	
	protected static String ID_WST_SNIPPETS_VIEW = "org.eclipse.wst.common.snippets.internal.ui.SnippetsView"; //$NON-NLS-1$	

	public void createInitialLayout(IPageLayout layout) {
		createLayout(layout);

		setupActions(layout);

		addShortcuts(layout);
	}

	private void addDBViewIfPresent(IPageLayout page, IFolderLayout bottomRight) {
		IViewDescriptor dbView = PlatformUI.getWorkbench().getViewRegistry().find(ID_DATA_VIEW);

		if (dbView != null) {
			bottomRight.addView(ID_DATA_VIEW);
		}
	}

	private void addTLViewIfPresent(IPageLayout layout, String topLeft) {
		IViewDescriptor tlView = PlatformUI.getWorkbench().getViewRegistry().find(ID_TASKLIST_VIEW);

		if (tlView != null) {
			IFolderLayout bottomTopLeft = layout.createFolder("bottomTopLeft", IPageLayout.BOTTOM, 0.7f, topLeft);
			bottomTopLeft.addView(ID_TASKLIST_VIEW);
		}
	}

	protected void addShortcuts(IPageLayout layout) {
		layout.addNewWizardShortcut(ID_NEW_PLUGIN_PROJECT_WIZARD);
		layout.addNewWizardShortcut(ID_NEW_PORTLET_WIZARD);
		layout.addNewWizardShortcut(ID_NEW_HOOK_WIZARD);
		layout.addNewWizardShortcut(ID_NEW_SERVICE_BUILDER_WIZARD);
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewPackageCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewClassCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewInterfaceCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.editors.wizards.UntitledTextFileWizard");//$NON-NLS-1$
		layout.addPerspectiveShortcut("org.eclipse.jst.j2ee.J2EEPerspective");
		layout.addPerspectiveShortcut("org.eclipse.jdt.ui.JavaPerspective");
		layout.addPerspectiveShortcut("org.eclipse.debug.ui.DebugPerspective");

		IPerspectiveDescriptor desc =
			PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId(
				"org.eclipse.team.cvs.ui.cvsPerspective");
		
		if (desc != null) {
			layout.addPerspectiveShortcut("org.eclipse.team.cvs.ui.cvsPerspective");
		}

		desc =
			PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId(
				"org.tigris.subversion.subclipse.ui.svnPerspective");
		
		if (desc != null) {
			layout.addPerspectiveShortcut("org.tigris.subversion.subclipse.ui.svnPerspective");
		}

		desc =
			PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId(
				"org.eclipse.team.svn.ui.repository.RepositoryPerspective");
		
		if (desc != null) {
			layout.addPerspectiveShortcut("org.eclipse.team.svn.ui.repository.RepositoryPerspective");
		}
	}

	protected void createLayout(IPageLayout layout) {
		// Editors are placed for free.
		String editorArea = layout.getEditorArea();

		// Top left.
		IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.20f, editorArea);//$NON-NLS-1$
		topLeft.addView(ID_PACKAGE_EXPLORER_VIEW);
		// topLeft.addView(ID_J2EE_HIERARCHY_VIEW);
		topLeft.addPlaceholder(ID_J2EE_HIERARCHY_VIEW);
		topLeft.addPlaceholder(IPageLayout.ID_RES_NAV);
		topLeft.addPlaceholder(JavaUI.ID_TYPE_HIERARCHY);
		topLeft.addPlaceholder(JavaUI.ID_PACKAGES_VIEW);

		addTLViewIfPresent(layout, "topLeft");

		// Top right.
		IFolderLayout topRight = layout.createFolder("topRight", IPageLayout.RIGHT, 0.7f, editorArea);//$NON-NLS-1$
		topRight.addView(IPageLayout.ID_OUTLINE);
		topRight.addPlaceholder(IPageLayout.ID_BOOKMARKS);

		IFolderLayout topRightBottom = layout.createFolder("topRightBottom", IPageLayout.BOTTOM, 0.7f, "topRight");
		topRightBottom.addView(IPageLayout.ID_PROP_SHEET);
		// Bottom top right

		// Left Top right.
		//		IFolderLayout leftTopRight = layout.createFolder("leftTopRight", IPageLayout.LEFT, 0.5f, "topRight");//$NON-NLS-1$
		// leftTopRight.addView(ID_WST_SNIPPETS_VIEW);

		// Bottom
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.7f, editorArea);//$NON-NLS-1$
		bottom.addView(ID_MARKERS_VIEW);
		
		addDBViewIfPresent(layout, bottom);
		
		bottom.addView(ID_SERVERS_VIEW);

		bottom.addPlaceholder(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addPlaceholder(ID_CONSOLE_VIEW);
		bottom.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);
		bottom.addPlaceholder(ID_SEARCH_VIEW);

	}

	protected void setupActions(IPageLayout layout) {
		layout.addActionSet("org.eclipse.jdt.ui.JavaActionSet"); //$NON-NLS-1$
		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		layout.addActionSet(IDebugUIConstants.DEBUG_ACTION_SET);
		layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);
		layout.addActionSet("com.liferay.ide.eclipse.ui.shortcuts.actionSet");

		layout.addShowViewShortcut(ID_J2EE_HIERARCHY_VIEW);
		layout.addShowViewShortcut(ID_SERVERS_VIEW);
		layout.addShowViewShortcut(ID_DATA_VIEW);
		layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
		layout.addShowViewShortcut(ID_WST_SNIPPETS_VIEW);
		layout.addShowViewShortcut(ID_MARKERS_VIEW);
		layout.addShowViewShortcut(ID_TASKLIST_VIEW);
		layout.addShowViewShortcut(ID_SEARCH_VIEW);
		layout.addShowViewShortcut(ID_CONSOLE_VIEW);

		layout.addShowInPart(ID_J2EE_HIERARCHY_VIEW);
	}
}