import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/users.service';
import { ChannelService } from '../../services/channels.service';
import { User } from '../../models/user.model';
import { Channel } from '../../models/channel.model';
import { USER_ID } from '../../constants/constants';
import { CreateChannelComponent } from '../create-channel/create-channel.component';
import { AddFriendComponent } from '../add-friend/add-friend.component';

@Component({
    selector: 'app-connections',
    templateUrl: './connections.component.html',
    styleUrls: ['./connections.component.css'],
    standalone: true,
    imports: [CommonModule, CreateChannelComponent, AddFriendComponent]
})
export class ConnectionsComponent implements OnInit {
    @Output() selection = new EventEmitter<{ type: 'channel' | 'friend'; id: number }>();

    userId = USER_ID;

    channels: Channel[] = [];
    friends: User[] = [];

    isCreateChannelModalVisible: boolean = false;
    isAddFriendModalVisible: boolean = false;

    constructor(
        private userService: UserService,
        private channelService: ChannelService
    ) { }

    ngOnInit(): void {
        this.loadConnections();
    }

    loadConnections(): void {
        this.userService.getUserConnections(this.userId).subscribe((response: { channels: Channel[], friends: User[] }) => {
            this.channels = response.channels;
            this.friends = response.friends;
        });
    }

    selectChannel(id: number): void {
        this.selection.emit({ type: 'channel', id });
    }

    selectFriend(id: number): void {
        this.selection.emit({ type: 'friend', id });
    }

    openCreateChannelModal(): void {
        this.isCreateChannelModalVisible = true;
    }

    closeCreateChannelModal(): void {
        this.isCreateChannelModalVisible = false;
    }

    onCreateChannel(channelName: string): void {
        this.channelService.createChannel(channelName, this.userId).subscribe(() => {
            this.closeCreateChannelModal();
            this.loadConnections();
        });
    }

    openAddFriendModal(): void {
        this.isAddFriendModalVisible = true;
    }

    closeAddFriendModal(): void {
        this.isAddFriendModalVisible = false;
    }

    onAddFriend(friendId: number): void {
        this.userService.addFriend(this.userId, friendId).subscribe(() => { 
            this.closeAddFriendModal();
            this.loadConnections();
        });
    }
}
