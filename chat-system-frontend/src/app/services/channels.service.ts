import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Member } from '../models/member.model';
import { Channel } from '../models/channel.model';
import { environment } from '../environments/environment';

@Injectable({
    providedIn: 'root',
})
export class ChannelService {
    private baseUrl = `${environment.apiUrl}/channels`

    constructor(private http: HttpClient) { }

    // Get channel details
    public getChannelDetails(channelId: number, userId: number): Observable<Channel> {
        return this.http.get<Channel>(
            `${this.baseUrl}/${channelId}?userId=${userId}`
        );
    }

    // Get channel members
    public getChannelMembers(channelId: number): Observable<Member[]> {
        return this.http.get<Member[]>(
            `${this.baseUrl}/${channelId}/members`
        );
    }

    // Create a new channel
    public createChannel(channelName: string, ownerId: number): Observable<Channel> {
        return this.http.post<Channel>(
            this.baseUrl,
            {
                name: channelName,
                ownerId: ownerId
            });
    }

    // Add a new member to a channel
    public addMemberToChannel(channelId: number, userId: number, adminId: number): Observable<Map<string, string>> {
        return this.http.post<Map<string, string>>(
            `${this.baseUrl}/${channelId}/members?adminId=${adminId}`,
            {
                userId
            }
        );
    }

    // Update a channel name
    public updateChannel(channelId: number, newName: string, adminId: number): Observable<Channel> {
        return this.http.put<Channel>(
            `${this.baseUrl}/${channelId}?adminId=${adminId}`,
            {
                name: newName
            }
        );
    }

    // Promote an admin role to a channel member
    public promoteMemberToAdmin(channelId: number, userId: number, ownerId: number): Observable<Map<string, string>> {
        return this.http.put<Map<string, string>>(
            `${this.baseUrl}/${channelId}/members/${userId}/promote?ownerId=${ownerId}`,
            { }
        );
    }

    // Delete a channel
    public deleteChannel(channelId: number, ownerId: number): Observable<Map<string, string>> {
        return this.http.delete<Map<string, string>>(
            `${this.baseUrl}/${channelId}?ownerId=${ownerId}`
        );
    }

    // Delete a member from a channel
    public removeMemberFromChannel(channelId: number, userId: number, ownerId: number): Observable<Map<string, string>> {
        return this.http.delete<Map<string, string>>(
            `${this.baseUrl}/${channelId}/members/${userId}?ownerId=${ownerId}`
        );
    }
}
