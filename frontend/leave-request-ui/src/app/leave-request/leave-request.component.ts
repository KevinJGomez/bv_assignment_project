import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-leave-request',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './leave-request.component.html',
  styleUrl: './leave-request.component.scss'
})
export class LeaveRequestComponent {
  leaveRequest = {
    leaveType: '',
    startDate: '',
    endDate: '',
    reason: ''
  };

  onSubmit() {
    console.log('Leave Request Submitted:', this.leaveRequest);
    // You can add your form submission logic here
  }
}
