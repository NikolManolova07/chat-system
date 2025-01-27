import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/users.service';
import { User } from '../../models/user.model';
import { USER_ID } from '../../constants/constants';

@Component({
    selector: 'app-add-friend',
    templateUrl: './add-friend.component.html',
    styleUrls: ['./add-friend.component.css'],
    standalone: true,
    imports: [CommonModule, FormsModule]
})
export class AddFriendComponent {
    @Input() friends: User[] = [];
    @Output() addFriend = new EventEmitter<number>();
    @Output() cancel = new EventEmitter<void>();

    userId = USER_ID;

    keyword: string = '';
    users: User[] = [];

    constructor(private userService: UserService) { }

    searchUsers(): void {
        if (this.keyword.trim()) {
            this.userService.searchUsers(this.keyword).subscribe((response) => {
                this.users = response.filter(user => user.id !== this.userId);
            });
        }
    }

    isAlreadyFriend(userId: number): boolean {
        return this.friends.some(friend => friend.id === userId);
    }

    onAdd(userId: number): void {
        this.addFriend.emit(userId);
    }

    onCancel(): void {
        this.cancel.emit();
        this.keyword = '';
    }
}