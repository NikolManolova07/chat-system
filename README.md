# Chat system
**Frontend & Backend Course Project**<br>

**1. Entity Relationship Diagram:**<br>

<img src="https://github.com/user-attachments/assets/5324269f-cf24-4fd3-b7b7-c5a0f0da4cd4" width="100%"><br>

**2. Chat System Project Structure:**<br>

**2.1. Frontend:**<br>
*- Angular, Visual Studio Code*

**2.2. Backend:**<br>
*- Spring Boot Rest API, IntelliJ IDEA*<br>

Dependencies:
- Spring Web;
- Spring Data JPA;
- Validation;
- H2 Database;
- Flyway.

**3. User Interface:**<br>

<img src="https://github.com/user-attachments/assets/dcbde554-8316-42b3-8ec9-831997d02ff4" width="100%"><br>

<img src="https://github.com/user-attachments/assets/2cb70dc8-aa5b-4ba0-b59d-cd7b7d64bb26" width="100%"><br>

<img src="https://github.com/user-attachments/assets/06448935-da1e-4bd2-966e-4c255ade5b9b" width="100%"><br>

<img src="https://github.com/user-attachments/assets/70315546-d152-4524-ade6-598d6629716d" width="100%"><br>


**4. Controllers:**<br>

**User Controller:**
| Method and Endpoint  | Result |
| ------------- | ------------- |
| GET /users/{userId} | Get a user by id. |
| GET /users/search?keyword={keyword} | List all users based on the search keyword. |
| GET /users/{userId}/connections | List all friends added by a user as well as the channels the user is a member of. |
| GET /users/{userId}/friends/{friendId} | List information about the friendship between users. |
| POST /users | Create a new user. |
| POST /users/friends | A user adds another user as a friend to their friend list. |

**Channel Controller:**
| Method and Endpoint  | Result |
| ------------- | ------------- |
| GET /channels/{channelId}?userId={userId} | List information about the channel where the user is a member. |
| GET /channels/{channelId}/members | List all members of a channel. |
| POST /channels | Create a new channel. |
| POST /channels/{channelId}/members?adminId={adminId} | An admin or owner adds a user as a member of their channel. |
| PUT /channels/{channelId}?adminId={adminId} | An admin or owner updates the name of their channel. |
| PUT /channels/{channelId}/members/{userId}/promote?ownerId={ownerId} | An owner assigns the admin role to a user in their channel. |
| DELETE /channels/{channelId}?ownerId={ownerId} | An owner deletes their own channel. |
| DELETE /channels/{channelId}/members/{userId}?ownerId={ownerId} | An owner removes a user from their channel. |

**Message Controller:**
| Method and Endpoint  | Result |
| ------------- | ------------- |
| GET /messages/direct/{friendId}?userId={userId} | List all messages directly between users that are friends. |
| GET /messages/channel/{channelId}?userId={userId} | List all messages in the channel where the user is a member. |
| POST /messages/direct | Create a direct message to a friend. |
| POST /messages/channel | Create a message in a channel. |
