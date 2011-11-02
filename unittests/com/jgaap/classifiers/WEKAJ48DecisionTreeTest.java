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
/**
 * 
 */
package com.jgaap.classifiers;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.Test;

import weka.classifiers.trees.J48;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author Amanda Kroft
 * 
 */
public class WEKAJ48DecisionTreeTest {

	/**
	 * Test method for {@link
	 * com.jgaap.classifiers.WEKAJ48DecisionTree#analyze(com.jgaap.generics.EventSet,
	 * List<EventSet>)}.
	 */
	@Test
	public void testAnalyze() {

		//Test 1

		//Create known texts
		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();

		known1.addEvent(new Event("mary"));
		known1.addEvent(new Event("had"));
		known1.addEvent(new Event("a"));
		known1.addEvent(new Event("little"));
		known1.addEvent(new Event("lamb"));
		known1.setAuthor("Mary");

		known2.addEvent(new Event("peter"));
		known2.addEvent(new Event("piper"));
		known2.addEvent(new Event("picked"));
		known2.addEvent(new Event("a"));
		known2.addEvent(new Event("peck"));
		known2.setAuthor("Peter");

		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);

		//Create unknown text
		EventSet unknown1 = new EventSet();

		unknown1.addEvent(new Event("mary"));
		unknown1.addEvent(new Event("had"));
		unknown1.addEvent(new Event("a"));
		unknown1.addEvent(new Event("little"));
		unknown1.addEvent(new Event("beta"));

		Vector<EventSet> uesv = new Vector<EventSet>();
		uesv.add(unknown1);

		//Classify unknown based on the knowns
		WEKAJ48DecisionTree tree = new WEKAJ48DecisionTree();
		List<List<Pair<String, Double>>> t = tree.analyze(uesv, esv);
		System.out.println(t.toString());
		J48 classifier = (J48)tree.classifier;
		if(classifier != null){
			System.out.println(classifier.toString());
		}

		//Assert that the authors match
		assertTrue(t.get(0).get(0).getFirst().equals("Mary"));
		
		
		//Test 2 - Add in third known author

		EventSet known3 = new EventSet();
		known3.addEvent(new Event("she"));
		known3.addEvent(new Event("sells"));
		known3.addEvent(new Event("seashells"));
		known3.addEvent(new Event("by"));
		known3.addEvent(new Event("seashore"));
		known3.setAuthor("Susie");

		esv.add(known3);

		t = tree.analyze(uesv, esv);
		System.out.println(t.toString());
		classifier = (J48)tree.classifier;
		if(classifier != null){
			System.out.println(classifier.toString());
		}

		assertTrue(t.get(0).get(0).getFirst().equals("Mary"));
		

		//Test 3 - Add in another unknown

		EventSet unknown2 = new EventSet();

		unknown2.addEvent(new Event("peter"));
		unknown2.addEvent(new Event("piper"));
		unknown2.addEvent(new Event("picked"));
		unknown2.addEvent(new Event("a"));
		unknown2.addEvent(new Event("shells"));

		uesv.add(unknown2);

		t = tree.analyze(uesv, esv);
		System.out.println(t.toString());
		classifier = (J48)tree.classifier;
		if(classifier != null){
			System.out.println(classifier.toString());
		}

		assertTrue(t.get(0).get(0).getFirst().equals("Mary") && t.get(1).get(0).getFirst().equals("Peter"));
	}

}