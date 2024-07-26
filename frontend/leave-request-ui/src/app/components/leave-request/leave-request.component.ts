import { Component, OnInit } from '@angular/core';
import { LeaveRequestService } from '../../services/leave-request.service';
import Swal from 'sweetalert2';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { catchError, map, first } from 'rxjs/operators';
import { FormGroup, Validators, AbstractControl, FormBuilder,FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { ValidatorFn } from '@angular/forms';

@Component({
  selector: 'app-leave-request',
  standalone: true,
  imports: [FormsModule,ReactiveFormsModule, CommonModule],
  templateUrl: './leave-request.component.html',
  styleUrl: './leave-request.component.scss'
})
export class LeaveRequestComponent implements OnInit{
  leaveForm!: FormGroup;
  sendData: any = {};
  today!: string;

  constructor(private fb: FormBuilder, private leaveRequestService: LeaveRequestService, private router: Router) {}

  ngOnInit(): void {
    this.leaveForm = this.fb.group({
      leaveType: new FormControl('', Validators.required),
      startDate: new FormControl('', Validators.required),
      endDate: new FormControl('', Validators.required),
      reason: new FormControl('', Validators.required),
    },{ validator: this.dateLessThan('startDate', 'endDate')});
    const todayDate = new Date();
    this.today = todayDate.toISOString().split('T')[0];
  }

  onSubmit() {
    if (this.leaveForm.status === 'INVALID') {
      if(this.leaveForm.errors?.['dateMismatch']){
        Swal.fire({
          icon: 'error',
          title: 'Invalid Date Selection!',
          text: 'Start date should be before the end date!',
        });
      }
      else{
        Swal.fire({
          icon: 'error',
          title: 'Invalid Submission',
          text: 'Provide Leave Information to Apply!',
        });
      }
      
    } 
    else {
      this.sendData = {
        leaveType: this.leaveForm.controls['leaveType'].value,
        startDate: this.leaveForm.controls['startDate'].value,
        endDate: this.leaveForm.controls['endDate'].value,
        reason: this.leaveForm.controls['reason'].value
      };
      this.leaveRequestService
        .saveRequest(this.sendData)
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
              title: 'Leave Application Failed!',
              text: data.data,
            });
          }
          else{
            Swal.fire({
              icon: 'success',
              title: 'Leave Application Applied!',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.leaveForm.reset()
            });
          }
          
        });
    }
  }

  resetForm(){
    this.leaveForm.reset();
  }

  dateLessThan(startDateField: string, endDateField: string): ValidatorFn {
    return (formGroup: AbstractControl): { [key: string]: any } | null => {
      const startDate = formGroup.get(startDateField)?.value;
      const endDate = formGroup.get(endDateField)?.value;
      if (startDate && endDate && startDate > endDate) {
        return { dateMismatch: true };
      }
      return null;
    };
  }
}
