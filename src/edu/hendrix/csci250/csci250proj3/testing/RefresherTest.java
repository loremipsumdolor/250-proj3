package edu.hendrix.csci250.csci250proj3.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.hendrix.csci250.csci250proj3.Refresher;

public class RefresherTest {

	@Test
	public void refresherTest() {
		try {
			Refresher.refreshDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
