import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LeaveRequestModalComponent } from '../leave-request-modal/leave-request-modal.component';

@Component({
  selector: 'app-leave-request-list',
  standalone: true,
  imports: [CommonModule,LeaveRequestModalComponent],
  templateUrl: './leave-request-list.component.html',
  styleUrl: './leave-request-list.component.scss'
})
export class LeaveRequestListComponent {
  leaveRequests = [
    {
      leaveType: 'Sick Leave',
      startDate: new Date('2024-07-01'),
      endDate: new Date('2024-07-03'),
      reason: 'Medical reasons'
    },
    {
      leaveType: 'Vacation',
      startDate: new Date('2024-08-01'),
      endDate: new Date('2024-08-10'),
      reason: 'Family trip'
    }
    // Add more leave requests here
  ];

  selectedRequest: any;
  showModal = false;

  viewRequest(request: any) {
    this.selectedRequest = { ...request };
    this.showModal = true;
  }

  updateRequest(updatedRequest: any) {
    const index = this.leaveRequests.findIndex(req => req.startDate === updatedRequest.startDate && req.endDate === updatedRequest.endDate);
    if (index > -1) {
      this.leaveRequests[index] = updatedRequest;
    }
  }

  deleteRequest(requestToDelete: any) {
    this.leaveRequests = this.leaveRequests.filter(req => req.startDate !== requestToDelete.startDate || req.endDate !== requestToDelete.endDate);
  }
}
