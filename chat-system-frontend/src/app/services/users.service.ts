import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { Channel } from '../models/channel.model';
import { Friend } from '../models/friend.model';
import { environment } from '../environments/environment';

@Injectable({
    providedIn: 'root',
})
export class UserService {
    private baseUrl = `${environment.apiUrl}/users`

    constructor(private http: HttpClient) { }

    // Get user details
    public getUserDetails(userId: number): Observable<User> {
        return this.http.get<User>(
            `${this.baseUrl}/${userId}`
        );
    }

    // Get all users by keyword search
    public searchUsers(keyword: string): Observable<User[]> {
        return this.http.get<User[]>(
            `${this.baseUrl}/search?keyword=${keyword}`
        );
    }

    // Get all user connections (channels and friends)
    public getUserConnections(userId: number): Observable<{ channels: Channel[], friends: User[] }> {
        return this.http.get<{ channels: Channel[], friends: User[] }>(
            `${this.baseUrl}/${userId}/connections`
        );
    }

    // Get friendship details
    public getFriendshipDetails(userId: number, friendId: number): Observable<Friend> {
        return this.http.get<Friend>(
            `${this.baseUrl}/${userId}/friends/${friendId}`
        );
    }

    // Create a friendship between users
    public addFriend(userId: number, friendId: number): Observable<Map<string, string>> {
        return this.http.post<Map<string, string>>(
            `${this.baseUrl}/friends`,
            {
                userId,
                friendId
            }
        );
    }
}
