import java.util.Collection;

public interface ISocialNetwork {

	/*
	 * join the social network and get an Account handle for logging in
	 */
	public Account join(String userName) throws UserExistsException;

	/*
	 * login using a valid Account handle -- only one user can be logged in
	 * returns an updated handle to member's account
	 */

	public Account login(Account me) throws UserNotFoundException;

	// log out: no user is logged in after a logout
	public void logout();

	// These operations requires a user to be logged in...

	// List all members visible to the logged-in user.
	public Collection<String> listMembers();

	/*
	 * Returns true if a member has joined the social network (if visible to
	 * logged-in user)
	 */
	public boolean hasMember(String userName);

	// Send a friend request to a valid, visible member
	public void sendFriendRequestTo(String userName) throws UserNotFoundException;

	/*
	 * Block a member from befriending the logged-in user: blocked members can't
	 * see the logged-in user
	 */
	public void block(String userName) throws UserNotFoundException;

	// Unblock a previously blocked member.
	public void unBlock(String userName) throws UserNotFoundException;

	// Unfriend and existing friend.
	public void unfriend(String userName) throws UserNotFoundException;

	// Accept a friend request from another visible member.
	public void acceptFriendRequestFrom(String userName) throws UserNotFoundException;

	// Reject a friend request from another member.
	public void rejectFriendRequestFrom(String userName) throws UserNotFoundException;

	/*
	 * Accept all friend requests automatically in the future, unless they are blocked by
	 * logged-in user. Once auto-acceptance is enabled, logged-in member does
	 * not need to call acceptFriendRequestFrom.
	 */
	public void autoAcceptFriendRequests();

	// Cancel auto-acceptance, and require explicit acceptance in the future. 
	public void acceptFriendRequestsExplicitly();

	/*
	 * Recommend friends to logged-in user: if two friends have a common friend,
	 * include that member in return Collection. Don't recommend members blocked
	 * by the logged-in user
	 */
	public Collection<String> recommendFriends();

	// Leave the social network and cease to exist to other members.
	public void leave();

	// No other public methods are allowed in ISocialNetwork implementations.
}
