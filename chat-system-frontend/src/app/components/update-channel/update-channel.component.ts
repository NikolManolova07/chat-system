import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-update-channel',
    templateUrl: './update-channel.component.html',
    styleUrls: ['./update-channel.component.css'],
    standalone: true,
    imports: [CommonModule, FormsModule]
})
export class UpdateChannelComponent {
    @Input() currentChannelName: string = '';
    @Output() updateChannel = new EventEmitter<string>();
    @Output() cancel = new EventEmitter<void>();

    newChannelName: string = '';

    onUpdate(): void {
        if (this.newChannelName.trim()) {
            this.updateChannel.emit(this.newChannelName.trim());
            this.newChannelName = '';
        }
    }

    onCancel(): void {
        this.cancel.emit();
        this.newChannelName = '';
    }
}