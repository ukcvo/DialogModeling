package edu.kit.anthropomatik.isl.DialogModeling.UserModel;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class SerializationHelperTest {

	private List<User> users;
	private static String FILE_NAME = "users_test.usr";
	
	@Before
	public void setup() {
		users = new ArrayList<User>();
		User.setIdCtr(0);
		User alice = new User("Alice", new UserProject("Dialog Modeling"));
		User bob = new User("Bob", new UserProject("Speech Recognition"));
		User cathie = new User("Cathie", new UserProject("System Integration"));
		bob.getCurrentProject().setProjectState(UserProjectState.GOOD);
		
		users.add(alice);
		users.add(cathie);
		users.add(bob);
	}
	
	@Test
	public void correctIds() {
		assertEquals(0, users.get(0).getId());
		assertEquals(1, users.get(2).getId());
		assertEquals(2, users.get(1).getId());
	}
	
	@Test
	public void store() {
		assertTrue(SerializationHelper.storeUsers(users, FILE_NAME));
	}
	
	@Test
	public void storeLoadSame() throws Exception {
		SerializationHelper.storeUsers(users, FILE_NAME);
		List<User> usersRead = SerializationHelper.loadUsers(FILE_NAME);
		assertEquals(users, usersRead);
	}
	
	@Test
	public void idCounterAfterLoad() throws Exception {
		SerializationHelper.storeUsers(users, FILE_NAME);
		List<User> usersRead = SerializationHelper.loadUsers(FILE_NAME);
		User diana = new User("Diana", new UserProject("Chilling"));
		assertEquals(usersRead.size(), diana.getId());
	}

	@AfterClass
	public static void teardown() {
		File f = new File(FILE_NAME);
		f.delete();
	}
}
