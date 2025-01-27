import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-create-channel',
    templateUrl: './create-channel.component.html',
    styleUrls: ['./create-channel.component.css'],
    standalone: true,
    imports: [CommonModule, FormsModule]
})
export class CreateChannelComponent {
    @Output() createChannel = new EventEmitter<string>();
    @Output() cancel = new EventEmitter<void>();

    channelName: string = '';

    onCreate(): void {
        if (this.channelName.trim()) {
            this.createChannel.emit(this.channelName);
            this.channelName = '';
        }
    }

    onCancel(): void {
        this.cancel.emit();
        this.channelName = '';
    }
}