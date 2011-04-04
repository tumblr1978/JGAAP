/**
 **/
package com.jgaap.canonicizers;

import java.awt.Color;

import com.jgaap.generics.Canonicizer;

/**
 * Changes length of all white spaces to 1. Any sequence of whitespaces
 * including newline, tab, and space, will become a single space in the
 * processed document.
 * 
 * @since 4.1
 **/
public class StripNumbers extends Canonicizer {

	@Override
	public String displayName() {
		return "Strip Numbers";
	}

	@Override
	public String tooltipText() {
		return "Converts numbers to a single # sign";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public Color guiColor() {
		return Color.MAGENTA;
	}

	/**
	 * strip numbers in argument
	 * 
	 * @param procText
	 *            Array of Characters to be processed
	 * @return Array of Characters after converting digit string to '#'
	 * 
	 */
	@Override
	public char[] process(char[] procText) {
		StringBuilder stringBuilder = new StringBuilder();
		boolean spaceflag = false;
		for (int i = 0; i < procText.length; i++) {
			if (Character.isDigit(procText[i]) && !spaceflag) {
				stringBuilder.append('#');
				spaceflag = true;
			} else if (!Character.isDigit(procText[i]) && procText[i] != ','
					&& procText[i] != '.') {
				// handle numbers like 3.14 and 20,000 as well
				// TODO : handle numbers like .001 or 12. (?)
//				processed.add(procText[i]);
				stringBuilder.append(procText[i]);
				spaceflag = false;
			}
		}
		return stringBuilder.toString().toCharArray();
	}
}
