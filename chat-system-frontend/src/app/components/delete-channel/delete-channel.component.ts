import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-delete-channel',
    templateUrl: './delete-channel.component.html',
    styleUrls: ['./delete-channel.component.css'],
    standalone: true,
    imports: [CommonModule]
})
export class DeleteChannelComponent {
    @Output() deleteConfirmed = new EventEmitter<void>();
    @Output() deleteCanceled = new EventEmitter<void>();

    confirmDeletion(): void {
        this.deleteConfirmed.emit();
    }

    cancelDeletion(): void {
        this.deleteCanceled.emit();
    }
}
