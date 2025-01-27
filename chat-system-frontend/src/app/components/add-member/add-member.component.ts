import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/users.service';
import { User } from '../../models/user.model';
import { Member } from '../../models/member.model';
import { USER_ID } from '../../constants/constants';

@Component({
    selector: 'app-add-member',
    templateUrl: './add-member.component.html',
    styleUrls: ['./add-member.component.css'],
    standalone: true,
    imports: [CommonModule, FormsModule]
})
export class AddMemberComponent {
    @Input() members: Member[] = [];
    @Output() addMember = new EventEmitter<number>();
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

    isAlreadyMember(userId: number): boolean {
        return this.members.some(member => member.user.id === userId);
    }

    onAdd(userId: number): void {
        this.addMember.emit(userId);
    }

    onCancel(): void {
        this.cancel.emit();
        this.keyword = '';
    }
}