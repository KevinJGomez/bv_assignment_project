import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthServiceService } from '../../../services/auth-service.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { catchError, map, first } from 'rxjs/operators';

@Component({
  selector: 'app-admin-console',
  standalone: true,
  imports: [FormsModule,CommonModule,ReactiveFormsModule],
  templateUrl: './admin-console.component.html',
  styleUrl: './admin-console.component.scss'
})
export class AdminConsoleComponent implements OnInit{
  myForm!: FormGroup;
  sendData: any = {};
  constructor(private authService: AuthServiceService, private router: Router) {}
  allusers: any = [];

  ngOnInit(): void {
    this.myForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', [Validators.required,Validators.minLength(6)]),
      role: new FormControl('', Validators.required) 
    });

    this.getAllUsers();
  }

  getAllUsers(){
    this.authService
      .getUsers()
      .then((data) => {
        console.log(data);
        this.allusers = data;
      })
      .catch((err) => {
        console.log(err);
      });
  }

  users = [
    { id: 1, username: 'john', role: 'ADMIN' },
    { id: 2, username: 'jane', role: 'USER' }
  ];

  onSubmit() {
    if (this.myForm.status === 'INVALID') {
      if(this.myForm.get('password')?.invalid){
        Swal.fire({
          icon: 'error',
          title: 'Invalid Submission',
          text: 'Password Must have atleast 6 characters!',
        });
      }
      else{
        Swal.fire({
          icon: 'error',
          title: 'Invalid Submission',
          text: 'Provide All Required Information!',
        });
      }
    } else {
      this.sendData = {
        username: this.myForm.controls['username'].value,
        password: this.myForm.controls['password'].value,
        role: this.myForm.controls['role'].value
      };
      this.authService
        .register(this.sendData)
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
              title: 'Creation Failed',
              text: data.data,
            });
          }
          else{
            Swal.fire({
              icon: 'success',
              title: 'User Creation Successful!',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.myForm.reset();
              this.getAllUsers();
            });
          }
          
        });
    }
  }

  resetForm(){
    this.myForm.reset();
  }
}
