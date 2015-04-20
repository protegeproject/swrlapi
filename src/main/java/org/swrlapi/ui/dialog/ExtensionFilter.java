package org.swrlapi.ui.dialog;

import javax.swing.filechooser.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A file filter the works on a particular extension.
 * <p>
 * Taken from Protege.
 *
 * @author Ray Fergerson fergerson@smi.stanford.edu
 */
public class ExtensionFilter extends FileFilter
{
	private final List<String> _extensions;
	private final String _description;

	public ExtensionFilter(Iterator<String> extensions, String description)
	{
		_extensions = new ArrayList<String>();
		while (extensions.hasNext()) {
			String extension = extensions.next();
			if (!extension.startsWith(".")) {
				extension = "." + extension;
			}
			_extensions.add(extension);
		}
		_description = description;
	}

	public ExtensionFilter(String extension, String description)
	{
		this(Collections.singleton(extension).iterator(), description);
	}

	@Override
	public boolean accept(java.io.File file)
	{
		if (file.isDirectory()) {
			return true;
		} else {
			String lowerCaseName = file.getName().toLowerCase();
			Iterator<String> it = _extensions.iterator();
			while (it.hasNext()) {
				String s = it.next();
				if (lowerCaseName.endsWith(s)) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public String getDescription()
	{
		String text;
		String es = "";
		Iterator<String> it = _extensions.iterator();
		while (it.hasNext()) {
			String s = it.next();
			es += "*" + s;
			if (it.hasNext()) {
				es += ", ";
			}
		}
		if (_description == null) {
			text = es + " files ";
		} else {
			text = _description + " (" + es + ")";
		}
		return text;
	}
}
