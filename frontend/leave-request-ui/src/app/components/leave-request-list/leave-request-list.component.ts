import { Component, ChangeDetectorRef, OnInit  } from '@angular/core';
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
export class LeaveRequestListComponent implements OnInit{
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
    this.selectedRequest = request;
    this.showModal = true;
  }

  updateRequest(updatedRequest: any) {
    this.getAllRequests();
  }

  closeRequest(updatedRequest: any) {
    this.showModal = false;
    this.getAllRequests();
  }

  deleteRequest(requestToDelete: any) {

  }
}

