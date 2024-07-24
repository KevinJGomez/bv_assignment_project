import { Component, EventEmitter, Input, Output, ChangeDetectorRef  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-leave-request-modal',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './leave-request-modal.component.html',
  styleUrl: './leave-request-modal.component.scss'
})
export class LeaveRequestModalComponent {
  @Input() show = false;
  @Input() leaveRequest: any = {};
  @Output() update = new EventEmitter<any>();
  @Output() delete = new EventEmitter<any>();
  @Output() close = new EventEmitter<void>();

  constructor(private cdr: ChangeDetectorRef) {}

  closeModal() {
    this.show = false;
    this.close.emit();
    this.cdr.detectChanges(); // Force change detection
  }

  updateRequest() {
    this.update.emit(this.leaveRequest);
    this.closeModal();
  }

  deleteRequest() {
    this.delete.emit(this.leaveRequest);
    this.closeModal();
  }
}
