import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/users.service';
import { ChannelService } from '../../services/channels.service';
import { Member } from '../../models/member.model';
import { Friend } from '../../models/friend.model';
import { Channel } from '../../models/channel.model';
import { USER_ID } from '../../constants/constants';
import { DeleteChannelComponent } from '../delete-channel/delete-channel.component';
import { UpdateChannelComponent } from '../update-channel/update-channel.component';
import { AddMemberComponent } from "../add-member/add-member.component";

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.css'],
    standalone: true,
    imports: [CommonModule, DeleteChannelComponent, UpdateChannelComponent, AddMemberComponent]
})
export class SettingsComponent implements OnChanges {
    @Input() type: 'channel' | 'friend' | null = null;
    @Input() id: number | null = null;
    @Output() refreshChannels = new EventEmitter<void>();

    userId = USER_ID;

    channel: Channel | null = null;
    members: Member[] = [];
    friend: Friend | null = null;

    isAddMemberModalVisible: boolean = false;
    isUpdateChannelModalVisible: boolean = false;
    isDeleteChannelModalVisible: boolean = false;

    constructor(
        private userService: UserService,
        private channelService: ChannelService
    ) { }

    ngOnChanges(): void {
        if (this.type && this.id !== null) {
            this.loadSettings();
        }
    }

    loadSettings(): void {
        if (this.type === 'channel') {
            this.channelService.getChannelDetails(this.id!, this.userId).subscribe((response) => this.channel = response);
            this.channelService.getChannelMembers(this.id!).subscribe((response) => this.members = response);
        } else if (this.type === 'friend') {
            this.userService.getFriendshipDetails(this.userId, this.id!).subscribe((response) => this.friend = response);
        }
    }

    isOwnerOrAdmin(): boolean {
        return this.members.some(
            (member) => member.user.id === this.userId &&
                (member.role === 'OWNER' || member.role === 'ADMIN')
        );
    }

    isOwner(): boolean {
        return this.channel!.owner.id === this.userId;
    }

    hasPermissionsForMember(member: Member): boolean {
        return this.channel!.owner.id === this.userId && member.role === 'GUEST';
    }

    makeAdmin(member: Member) {
        this.channelService.promoteMemberToAdmin(this.channel!.id, member.user.id, this.userId).subscribe(() => this.loadSettings());
    }

    removeMember(member: Member) {
        this.channelService.removeMemberFromChannel(this.channel!.id, member.user.id, this.userId).subscribe(() => this.loadSettings());
    }

    openAddMemberModal(): void {
        this.isAddMemberModalVisible = true;
    }

    closeAddMemberModal(): void {
        this.isAddMemberModalVisible = false;
    }

    onAddMember(memberId: number): void {
        this.channelService.addMemberToChannel(this.channel!.id, memberId, this.userId).subscribe(() => {
            this.closeAddMemberModal();
            this.loadSettings();
        });
    }

    openUpdateChannelModal(): void {
        this.isUpdateChannelModalVisible = true;
    }

    closeUpdateChannelModal(): void {
        this.isUpdateChannelModalVisible = false;
    }

    onUpdateChannel(channelName: string): void {
        this.channelService.updateChannel(this.channel!.id, channelName, this.userId).subscribe(() => {
            this.closeUpdateChannelModal();
            this.refreshChannels.emit();
        });
    }

    openDeleteChannelModal(): void {
        this.isDeleteChannelModalVisible = true;
    }

    closeDeleteChannelModal(): void {
        this.isDeleteChannelModalVisible = false;
    }

    onDeleteChannel(): void {
        this.channelService.deleteChannel(this.channel!.id, this.userId).subscribe(() => {
            this.closeDeleteChannelModal();
            this.refreshChannels.emit();
        });
    }
}