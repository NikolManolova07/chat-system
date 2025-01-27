import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Message } from '../models/message';
import { environment } from '../environments/environment';

@Injectable({
    providedIn: 'root',
})
export class MessageService {
    private baseUrl = `${environment.apiUrl}/messages`

    constructor(private http: HttpClient) { }

    // Create a new direct message to a friend
    public sendDirectMessage(senderId: number, receiverId: number, content: string): Observable<Map<string, string>> {
        return this.http.post<Map<string, string>>(`${this.baseUrl}/direct`, {
            senderId,
            receiverId,
            content,
        });
    }
    
    // Get all direct messages with a friend
    public getDirectMessages(userId: number, friendId: number): Observable<Message[]> {
        return this.http.get<Message[]>(
            `${this.baseUrl}/direct/${friendId}?userId=${userId}`
        );
    }

    // Create a new channel message
    public sendChannelMessage(senderId: number, channelId: number, content: string): Observable<Map<string, string>> {
        return this.http.post<Map<string, string>>(`${this.baseUrl}/channel`, {
            senderId,
            channelId,
            content,
        });
    }

    // Get all channel messages
    public getChannelMessages(userId: number, channelId: number): Observable<Message[]> {
        return this.http.get<Message[]>(
            `${this.baseUrl}/channel/${channelId}?userId=${userId}`
        );
    }
}
