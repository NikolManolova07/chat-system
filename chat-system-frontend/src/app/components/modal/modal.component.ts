import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.css'],
    standalone: true,
    imports: [CommonModule]
})
export class ModalComponent {
    @Input() message: string = '';
    @Output() closeModal = new EventEmitter<void>();

    close(): void {
      this.closeModal.emit();
    }
}
