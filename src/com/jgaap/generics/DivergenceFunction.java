/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jgaap.generics;

import com.jgaap.generics.DivergenceType;

/**
 * 
 * Abstract class for divergences (distance like functions where A->B != B->A)
 * 
 * @author Michael Ryan
 * @since 4.5.0
 */

public abstract class DivergenceFunction extends DistanceFunction {
	
	public DivergenceFunction() {
		addParams("divergenceType", "Divergence Type", "STANDARD", new String[] {"STANDARD", "AVERAGE", "MAX", "MIN", "REVERSE", "CROSS"}, false);
	}
	
	@Override
	public double distance(EventMap eventMap1, EventMap eventMap2) throws DistanceCalculationException {
		double dist;
		double first;
		double second;
		DivergenceType divergenceType = DivergenceType.valueOf(getParameter(
				"divergenceType", "STANDARD").toUpperCase());
		switch (divergenceType.ordinal()) {
		case 1:
			dist = (divergence(eventMap1, eventMap2) + divergence(eventMap2, eventMap1)) / 2.0;
			break;
		case 2:
			first = divergence(eventMap1, eventMap2);
			second = divergence(eventMap1, eventMap2);
			dist = (first > second ? first : second);
			break;
		case 3:
			first = divergence(eventMap1, eventMap2);
			second = divergence(eventMap1, eventMap2);
			dist = (first < second ? first : second);
			break;
		case 4:
			dist = divergence(eventMap2, eventMap1);
			break;
		case 5:
			first = divergence(eventMap1, eventMap2);
			second = divergence(eventMap1, eventMap2);
			dist = first * second;
            break;
		case 0:
		default:
			dist = divergence(eventMap1, eventMap2);
			break;
		}
		return dist;
	}

	abstract protected double divergence(EventMap eventMap1, EventMap eventMap2) throws DistanceCalculationException;

	@Override
	abstract public boolean showInGUI();

	@Override
	abstract public String tooltipText();

}
