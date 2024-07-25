import { Component, ChangeDetectorRef  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LeaveRequestModalComponent } from '../leave-request-modal/leave-request-modal.component';
import { LeaveRequestService } from '../../services/leave-request.service';
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
  allRequests: any = [];

  selectedRequest: any = null;
  showModal = false;

  constructor(private cdr: ChangeDetectorRef, private leaveRequestService: LeaveRequestService) {}

  ngOnInit() {
    this.getAllRequests();
  }

  getAllRequests(){
    this.leaveRequestService
      .getRequests()
      .then((data) => {
        // console.log(data);
        this.allRequests = data;
      })
      .catch((err) => {
        console.log(err);
      });
  }
  
  viewRequest(request: any) {
    this.selectedRequest = { ...request };
    this.showModal = true;
  }

  updateRequest(updatedRequest: any) {
    const index = this.leaveRequests.findIndex(req => req.startDate === updatedRequest.startDate && req.endDate === updatedRequest.endDate);
    if (index > -1) {
      this.leaveRequests[index] = updatedRequest;
    }
    this.showModal = false;
    this.cdr.detectChanges(); // Force change detection
  }

  deleteRequest(requestToDelete: any) {
    this.leaveRequests = this.leaveRequests.filter(req => req.startDate !== requestToDelete.startDate || req.endDate !== requestToDelete.endDate);
    this.showModal = false;
    this.cdr.detectChanges(); // Force change detection
  }
}
