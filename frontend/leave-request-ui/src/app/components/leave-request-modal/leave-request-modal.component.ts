import { Component, EventEmitter, Input, Output, ChangeDetectorRef, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LeaveRequestService } from '../../services/leave-request.service';
import Swal from 'sweetalert2';
import { catchError, map, first } from 'rxjs/operators';

@Component({
  selector: 'app-leave-request-modal',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './leave-request-modal.component.html',
  styleUrl: './leave-request-modal.component.scss'
})
export class LeaveRequestModalComponent implements OnInit{
  @Input() show = false;
  @Input() leaveRequestId!:Number;
  @Output() update = new EventEmitter<any>();
  @Output() delete = new EventEmitter<any>();
  @Output() close = new EventEmitter<void>();
  allRequests: any = [];
  filteredRequest: any = [];
  leaveRequest = {
    id: 0,
    leaveType: '',
    startDate: new Date(),
    endDate: new Date(),
    reason: '',
    userId: 0
  };
  sendData: any = {};

  constructor(private cdr: ChangeDetectorRef, private leaveRequestService: LeaveRequestService) {}

  ngOnInit() {
    this.getAllRequests();
  }

  closeModal() {
    this.show = false;
    this.close.emit();
    this.cdr.detectChanges(); // Force change detection
  }

  updateRequest() {
    if (
      !this.leaveRequest.leaveType ||
      !this.leaveRequest.startDate ||
      !this.leaveRequest.endDate ||
      !this.leaveRequest.reason
    ) {
      Swal.fire({
        icon: 'error',
        title: 'Invalid Leave Update!',
        text: 'Provide All Required Fields!',
      });
    }
    else{
      this.sendData = {
        id: this.leaveRequestId,
        leaveType: this.leaveRequest.leaveType,
        startDate: this.leaveRequest.startDate,
        endDate: this.leaveRequest.endDate,
        reason: this.leaveRequest.reason
      }
      this.leaveRequestService
        .updateRequest(this.sendData)
        .pipe(
          first(),
          catchError((err) => {
            return err;
          })
        )
        .subscribe((data: any) => {
          if(data.resCode !== "0"){
            Swal.fire({
              icon: 'error',
              title: 'Leave Application Update Failed!',
              text: data.data,
            });
          }
          else{
            Swal.fire({
              icon: 'success',
              title: 'Leave Application Updated!',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.closeModal();
            });
          }
          
        });
      
    }
    
    
  }

  deleteRequest() {
    Swal.fire({
      title: 'Delete a Leave Application',
      text: 'Are you sure you want to Delete ' + this.leaveRequest.leaveType + ' Leave?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes',
      cancelButtonText: 'No'
    }).then((result) => {
      if (result.isConfirmed) {
        this.leaveRequestService
        .deleteRequest(this.leaveRequestId)
        .pipe(
          first(),
          catchError((err) => {
            return err;
          })
        )
        .subscribe((data: any) => {
          if(data.resCode !== "0"){
            Swal.fire({
              icon: 'error',
              title: 'Leave Application Deletion Failed!',
              text: data.data,
            });
          }
          else{
            Swal.fire({
              icon: 'success',
              title: 'Leave Application Deleted Successfully!',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.closeModal();
            });
          }
          
        });
      }
    });
    
  }

  getAllRequests(){
    this.leaveRequestService
      .getRequests()
      .then((data) => {
        this.allRequests = data;
        this.filterRequest(data)
      })
      .catch((err) => {
      });
  }

  filterRequest(data: any = []) {
    for (let i = 0; i < data.length; i++) {
      if (data[i].id == this.leaveRequestId) {
        this.leaveRequest.leaveType = data[i].leaveType;
        this.leaveRequest.startDate = data[i].startDate;
        this.leaveRequest.endDate = data[i].endDate;
        this.leaveRequest.reason = data[i].reason;

      }
    }
  }
}

