/*******************************************************************************
 * Copyright (c) 2010-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.ide.eclipse.taglib.ui.model.internal;

import org.eclipse.sapphire.modeling.xml.XmlElement;
import org.eclipse.sapphire.modeling.xml.XmlValueBindingImpl;


public class DescriptionBinding extends XmlValueBindingImpl {

	@Override
	public String read() {
		String value = null;

		final XmlElement element = xml(false);

		XmlElement desc = element.getChildElement("description", false);

		if (desc != null) {
			value = desc.getText();

			// remove everything that is in a comment
			if (value != null) {
				value = value.replaceAll("<!--.*-->", "");
			}
		}

		return value;
	}

	@Override
	public void write(String value) {
	}

}
