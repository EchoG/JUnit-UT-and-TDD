import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class AccountTest {
	
Account me, her, another;
	
	@Before
	public void setUp() throws Exception {
		me = new Account("Hakan");
		her = new Account("Serra");
		another = new Account("Cecile");
	}

	@Test
	public void testOneFriendReq() {
		me.befriend(her);
		assertTrue(me.whoWantsToBeFriends().contains(her.getUserName()));
	}
	
	@Test
	public void noFriendReq() {
		assertEquals(0, me.whoWantsToBeFriends().size());
	}
	
	@Test
	public void testTwoFriendReq() {
		me.befriend(her);
		me.befriend(another);
		//assertEquals(2, me.whoWantsToBeFriends().size());
		assertTrue(me.whoWantsToBeFriends().contains(another.getUserName()));
		assertTrue(me.whoWantsToBeFriends().contains(her.getUserName()));
	}
	
	@Test
	public void doubleFriendReqOk() {
		me.befriend(her);
		me.befriend(her);
		assertEquals(1, me.whoWantsToBeFriends().size());
		assertEquals(1, her.whoDidIAskToBefriend().size());
	}
	
	@Test
	public void afterAcceptFriendReqWhoWantsToBeFriendsUpdated() {
		me.befriend(her);
		her.accepted(me);
		assertFalse(me.whoWantsToBeFriends().contains(her.getUserName()));
	}
	
	@Test
	public void everybodyAreFriends() {
		me.befriend(her);
		me.befriend(another);
		her.befriend(another);
		her.accepted(me);
		her.accepted(another);
		another.accepted(her);
		another.accepted(me);
		assertTrue(me.hasFriend(her.getUserName()));
		assertTrue(me.hasFriend(another.getUserName()));
		assertTrue(her.hasFriend(me.getUserName()));
		assertTrue(her.hasFriend(another.getUserName()));
		assertTrue(another.hasFriend(her.getUserName()));
		assertTrue(another.hasFriend(me.getUserName()));
	}
	
	@Test
	public void cannotBefriendAnExistingFriend() {
		me.befriend(her);
		her.accepted(me);
		assertTrue(her.hasFriend(me.getUserName()));
		me.befriend(her);
		assertFalse(me.whoWantsToBeFriends().contains(her.getUserName()));
		//assertFalse(her.whoWantsToBeFriends().contains(me.getUserName()));
	}
	
	@Test
	public void cannotBefriendWithNull() {
		me.befriend(null);
		assertFalse(me.whoWantsToBeFriends().contains(null));
	}
	
	@Test
	public void cannotAcceptAMemberWithoutRequest() {
		her.accepted(me);
		assertFalse(me.hasFriend(her.getUserName()));
		assertFalse(her.hasFriend(me.getUserName()));
	}

}
