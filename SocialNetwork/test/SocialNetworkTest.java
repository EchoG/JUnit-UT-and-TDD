import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SocialNetworkTest {

	SocialNetwork sn;
	@Before
	public void setUp() throws Exception {
		sn = new SocialNetwork();
	}

	@After
	public void tearDown() throws Exception {
	
	}

	@Test 
	public void canJoinSocialNetwork() {
		Account me = sn.join("Hakan");
		assertEquals("Hakan", me.getUserName());
	}
	
	@Test 
	public void cannotReJoinSocialNetwork() {
		Account me = sn.join("Hakan");
		Account her = sn.join("Hakan");
		assertEquals(null, her);
	}
	
	@Test 
	public void cannotJoinSocialNetworkForNullUsername() {
		Account me = sn.join(null);
		Account her = sn.join("");
		Collection<String> members = sn.listMembers();
		assertFalse(members.contains(""));
		assertFalse(members.contains(null));
	}
	
	@Test 
	public void canListSingleMemberOfSocialNetwork() {
		sn.join("Hakan");
		Collection<String> members = sn.listMembers();
		//assertEquals(1, members.size());
		assertTrue(members.contains("Hakan"));
	}
	
	@Test
	public void testSocialNetworkSizeOfOneMember(){
		sn.join("Hakan");
		Collection<String> members = sn.listMembers();
		assertEquals(1, members.size());
	}
	
	@Test 
	public void twoPeopleCanJoinSocialNetwork() {
		sn.join("Hakan");
		sn.join("Cecile");
		Collection<String> members = sn.listMembers();
		//assertEquals(2, members.size());
		assertTrue(members.contains("Hakan"));
		assertTrue(members.contains("Cecile"));
	}
	
	@Test
	public void testSocialNetworkSizeOfTwoMember(){
		sn.join("Hakan");
		sn.join("Cecile");
		Collection<String> members = sn.listMembers();
		assertEquals(2, members.size());
	}
	
	@Test 
	public void aMemberCanSendAFriendReqToAnother() {
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		try {
			sn.login(me);
			sn.sendFriendRequestTo("Cecile");
			
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(her.whoWantsToBeFriends().contains("Hakan"));
	}
	
	@Test 
	public void aMemberCanAcceptAFriendRequestFromAnother() {
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		
		try {
			sn.login(me);
			sn.sendFriendRequestTo("Cecile");
			sn.login(her);
			sn.acceptFriendRequestFrom("Hakan");
			
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(me.hasFriend("Cecile"));
		assertTrue(her.hasFriend("Hakan"));
		
	}
	
	@Test
	public void listAllFriendReqsToOthers(){
		Account me = sn.join("Hakan");
		try {
			sn.login(me);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Account her = sn.join("Cecile");
		Account lily = sn.join("Lily");
		Account lucy = sn.join("Lucy");
		try {
			sn.sendFriendRequestTo("Cecile");
			sn.sendFriendRequestTo("Lily");
			sn.sendFriendRequestTo("Lucy");
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(3, me.whoDidIAskToBefriend().size());
		
	}
	
	@Test
	public void acceptAllPendingReqs(){
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		Account lily = sn.join("Lily");
		Account lucy = sn.join("Lucy");
		try {
			sn.login(her);
			sn.sendFriendRequestTo("Hakan");
			sn.login(lily);
			sn.sendFriendRequestTo("Hakan");
			sn.login(lucy);
			sn.sendFriendRequestTo("Hakan");
			sn.login(me);
			sn.acceptAllFriendRequestsTo();
			
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(me.hasFriend(her.getUserName()));
		assertTrue(me.hasFriend(lily.getUserName()));
		assertTrue(me.hasFriend(lucy.getUserName()));
		assertTrue(her.hasFriend(me.getUserName()));
		assertTrue(lily.hasFriend(me.getUserName()));
		assertTrue(lucy.hasFriend(me.getUserName()));
		
	}
	
	@Test
	public void rejectAFriendReq() {
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		
		try {
			sn.login(me);
			sn.sendFriendRequestTo("Cecile");
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(her.whoWantsToBeFriends().contains("Hakan"));
		
		try {
			sn.login(her);
			sn.rejectFriendRequestFrom("Hakan");
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(her.whoWantsToBeFriends().contains("Hakan"));
		
	}
	
	@Test
	public void rejectAllPendingReqs(){
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		Account lily = sn.join("Lily");
		Account lucy = sn.join("Lucy");
		try {
			sn.login(her);
			sn.sendFriendRequestTo("Hakan");
			sn.login(lily);
			sn.sendFriendRequestTo("Hakan");
			sn.login(lucy);
			sn.sendFriendRequestTo("Hakan");
			sn.login(me);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(me.whoWantsToBeFriends().contains("Cecile"));
		assertTrue(me.whoWantsToBeFriends().contains("Lily"));
		assertTrue(me.whoWantsToBeFriends().contains("Lucy"));
		try {
			sn.rejectAllFriendRequestsTo();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(me.whoWantsToBeFriends().contains("Cecile"));
		assertFalse(me.whoWantsToBeFriends().contains("Lily"));
		assertFalse(me.whoWantsToBeFriends().contains("Lucy"));
		
	}
	
	@Test
	public void autoAcceptFriendReqs(){
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		Account lily = sn.join("Lily");
		try {
			sn.login(me);
			sn.autoAcceptFriendRequests();
			sn.login(her);
			sn.sendFriendRequestTo("Hakan");
			sn.login(lily);
			sn.sendFriendRequestTo("Hakan");
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(me.hasFriend(her.getUserName()));
		assertTrue(me.hasFriend(lily.getUserName()));
		assertTrue(her.hasFriend(me.getUserName()));
		assertTrue(lily.hasFriend(me.getUserName()));
		
	}
	
	@Test
	public void aMemberUnfriendAnExitingFriend(){
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		try {
			sn.login(me);
			sn.sendFriendRequestTo("Cecile");
			sn.login(her);
			sn.acceptFriendRequestFrom("Hakan");
			sn.unfriend("Hakan");
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertFalse(me.hasFriend(her.getUserName()));
		assertFalse(her.hasFriend(me.getUserName()));
	}
	
	@Test
	public void testRemoveAMemberFromSN() throws UserNotFoundException{
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		Account lily = sn.join("Lily");
		Account lucy = sn.join("Lucy");
		sn.login(me);
		sn.sendFriendRequestTo("Cecile");
		sn.login(her);
		sn.acceptFriendRequestFrom("Hakan");
		sn.login(me);
		sn.sendFriendRequestTo("Lily");
		sn.login(lucy);
		sn.sendFriendRequestTo("Hakan");
		sn.login(me);
		sn.leave();
		assertFalse(her.hasFriend("Hakan"));
		assertFalse(lily.whoWantsToBeFriends().contains("Hakan"));
		assertFalse(lucy.whoDidIAskToBefriend().contains("Hakan"));
	}
	
	@Test
	public void doubleAcceptRequestOK() throws UserNotFoundException{
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		sn.login(me);
		sn.sendFriendRequestTo("Cecile");
		sn.login(her);
		sn.acceptFriendRequestFrom("Hakan");
		assertEquals(1, her.getFriends().size());
		sn.acceptFriendRequestFrom("Hakan");
		assertEquals(1, her.getFriends().size());
	}
	
	@Test
	public void cannotAcceptWithoutRequest() throws UserNotFoundException{
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		sn.login(her);
		sn.acceptFriendRequestFrom("Hakan");
		assertEquals(0, her.getFriends().size());
	}
	
	@Test
	public void doubleRequestOK(){
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		try {
			sn.login(me);
			sn.sendFriendRequestTo("Cecile");
			sn.sendFriendRequestTo("Cecile");
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(1, me.whoDidIAskToBefriend().size());
		assertEquals(1, her.whoWantsToBeFriends().size());
	}

	@Test
	public void testSendRequestToNull(){
		Account me = sn.join("Hakan");
		try {
			sn.login(me);
			sn.sendFriendRequestTo("Cecile");
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, me.whoDidIAskToBefriend().size());
	}
	
	@Test
	public void testAcceptFriendFromNull() throws UserNotFoundException{
		Account me = sn.join("Hakan");
		Account her = new Account("Cecile");
		sn.login(me);
		sn.acceptFriendRequestFrom("Cecile");
		assertEquals(0, me.getFriends().size());
	}
	
	@Test
	public void acceptAllPendingReqsContainingNull(){
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		Account lily = sn.join("Lily");
		Account lucy = new Account("lucy");
		try {
			sn.login(her);
			sn.sendFriendRequestTo("Hakan");
			sn.login(lily);
			sn.sendFriendRequestTo("Hakan");
			sn.login(lucy);
			sn.sendFriendRequestTo("Hakan");
			sn.login(me);
			sn.acceptAllFriendRequestsTo();
		} catch (UserNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		assertTrue(me.hasFriend(her.getUserName()));
		assertTrue(me.hasFriend(lily.getUserName()));
		assertFalse(me.hasFriend(lucy.getUserName()));
		assertTrue(her.hasFriend(me.getUserName()));
		assertTrue(lily.hasFriend(me.getUserName()));
		assertFalse(lucy.hasFriend(me.getUserName()));
		
	}
	
	@Test
	public void rejectAFriendReqWithoutReq() throws UserNotFoundException {
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		sn.login(me);
		sn.rejectFriendRequestFrom("Cecile");
		assertFalse(me.whoWantsToBeFriends().contains("Cecile"));
		assertEquals(0, me.whoWantsToBeFriends().size());
		
	}
	
	@Test
	public void aMemberUnfriendAnNonExitingFriend() throws UserNotFoundException{
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		sn.login(me);
		sn.sendFriendRequestTo("Cecile");
		assertFalse(me.hasFriend(her.getUserName()));
		assertFalse(her.hasFriend(me.getUserName()));
		assertEquals(0, me.getFriends().size());
	}
	
	//B2
	@Test
	public void testLogin(){
		try {
			Account me = new Account("Hakan");// test Hakan login
			sn.login(me);
			assertEquals(me, sn.loginMember);
			Account her = new Account("Cecile");//test another one Cecile try to login and change the loginMember
			sn.login(her);
			assertEquals(her, sn.loginMember);	
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//B3
	@Test
	public void testHasMember(){
		sn.join("Chenran");
		assertTrue(sn.hasMember("Chenran"));
	}
	
	//B4
	@Test
	public void acceptRequestsExplicitly(){
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		try {
			me = sn.login(me);
			sn.acceptFriendRequestsExplicitly();
			sn.sendFriendRequestTo("Cecile");
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertFalse(me.hasFriend(her.getUserName()));
		
	}
	
	//B5
	@Test
	public void testBlock(){
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		try {
			sn.login(me);
			sn.sendFriendRequestTo("Cecile");
			sn.login(her);
			sn.acceptFriendRequestFrom("Hakan");
			sn.login(me);
			sn.block("Cecile");
			sn.login(her);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(sn.listMembers().contains(me.getUserName()));//the blocked people cannot list the logged-in member
		assertFalse(me.hasFriend("Cecile"));
		
	}
	
	//B6
	@Test
	public void testUnblock(){
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		
		try {
			sn.login(me);
			sn.sendFriendRequestTo("Cecile");
			sn.login(her);
			sn.acceptFriendRequestFrom("Hakan");
			sn.login(me);
			sn.block("Cecile");
			assertFalse(sn.listMembers().contains(her));
			assertFalse(me.hasFriend("Cecile"));
			sn.unBlock("Cecile");
		    sn.login(her);
		    assertTrue(sn.listMembers().contains(me.getUserName()));
			sn.sendFriendRequestTo("Hakan");// cecile sent request to hakan
			sn.login(me);
			sn.acceptFriendRequestFrom("Cecile");// hakan accept from cecile
			assertTrue(me.hasFriend("Cecile"));
			assertTrue(her.hasFriend("Hakan"));
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// B7:
	@Test
	public void recommendFriends(){
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		Account lucy = sn.join("Lucy");
		Account lily = sn.join("Lily"); // need to be recommended
		
		try {
			sn.login(her);
			sn.sendFriendRequestTo("Hakan");// Cecile sent request to hakan
			sn.login(me);
			sn.acceptFriendRequestFrom("Cecile");// hakan accept request from cecile
			sn.login(lucy);
			sn.sendFriendRequestTo("Hakan");// lucy send request to hakan
			sn.login(me);
			sn.acceptFriendRequestFrom("Lucy"); // hakan accept request from lucy
			sn.login(her);
			sn.sendFriendRequestTo("Lily");//cecile send request to lily
			sn.login(lily);
			sn.acceptFriendRequestFrom("Cecile");// lily accept request from cecile
			sn.login(lucy);
			sn.sendFriendRequestTo("Lily");//lucy send request to lily
			sn.login(lily);
			sn.acceptFriendRequestFrom("Lucy");// lily accept request from lucy
			sn.login(me);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//cecile send request to hakan
		
		assertFalse(me.hasFriend("Lily"));
		assertTrue(sn.recommendFriends().contains("Lily"));
	}
	
	//B8
	@Test
	public void blockTerminateRelation() throws UserNotFoundException{
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		Account bob = sn.join("Bob");
		Account chris = sn.join("Chris");
		
		sn.login(me);
		sn.sendFriendRequestTo("Cecile");
		sn.login(her);
		sn.acceptFriendRequestFrom("Hakan");//Cecile and Hakan are friends
		sn.login(bob);
		sn.sendFriendRequestTo("Hakan");// Bob sends request to Hakan
		sn.login(me);
		sn.sendFriendRequestTo("Chris");//Hakan sends request to Chris
		sn.block("Cecile");
		sn.block("Bob");
		sn.block("Chris");
		
		assertFalse(me.hasFriend("Cecile"));
		assertFalse(me.whoWantsToBeFriends().contains("Bob"));
		assertFalse(me.whoDidIAskToBefriend().contains("Chris"));
		
	}


}
