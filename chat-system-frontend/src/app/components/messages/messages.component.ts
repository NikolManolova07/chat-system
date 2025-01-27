import { Component, Input, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService } from '../../services/messages.service';
import { Message } from '../../models/message';
import { USER_ID } from '../../constants/constants';
import { ModalComponent } from '../modal/modal.component';

@Component({
    selector: 'app-messages',
    templateUrl: './messages.component.html',
    styleUrls: ['./messages.component.css'],
    standalone: true,
    imports: [CommonModule, FormsModule, ModalComponent]
})
export class MessagesComponent implements OnChanges {
    @Input() type: 'channel' | 'friend' | null = null;
    @Input() id: number | null = null;

    userId = USER_ID;

    messages: Message[] = [];
    newMessage:string = '';
    errorMessage: string = '';
    
    isMessageModalVisible: boolean = false;

    constructor(private messageService: MessageService) { }

    ngOnChanges(): void {
        if (this.type && this.id !== null) {
            this.loadMessages();
        }
    }

    loadMessages(): void {
        if (this.type === 'channel') {
            this.messageService.getChannelMessages(this.userId, this.id!).subscribe((response) => this.messages = response);
        } else if (this.type === 'friend') {
            this.messageService.getDirectMessages(this.userId, this.id!).subscribe((response) => this.messages = response);
        }
    }

    sendMessage(): void {
        if (!this.newMessage.trim()) {
            this.errorMessage = 'Message cannot be empty!';
            this.isMessageModalVisible = true;
            return;
        }
        if (this.type === 'channel') {
            this.messageService.sendChannelMessage(this.userId, this.id!, this.newMessage).subscribe(() => this.loadMessages());
        } else if (this.type === 'friend') {
            this.messageService.sendDirectMessage(this.userId, this.id!, this.newMessage).subscribe(() => this.loadMessages());
        }
        this.newMessage = '';
    }

    closeModal(): void {
        this.errorMessage = '';
        this.isMessageModalVisible = false;
    }
}