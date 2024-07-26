import { Component, OnInit} from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators
} from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, map, first } from 'rxjs/operators';
import { AuthServiceService } from '../../../services/auth-service.service';
import Swal from 'sweetalert2';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{
  myForm!: FormGroup;
  sendData: any = {};

  constructor(private authService: AuthServiceService, private router: Router) {}

  ngOnInit(): void {
    this.myForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  onSubmit() {
    if (this.myForm.status === 'INVALID') {
      Swal.fire({
        icon: 'error',
        title: 'Invalid Submission',
        text: 'Provide Credentials to Login!',
      });
    } else {
      this.sendData = {
        username: this.myForm.controls['username'].value,
        password: this.myForm.controls['password'].value,
      };
      this.authService
        .login(this.sendData)
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
              title: 'Login Failed',
              text: data.data,
            });
          }
          else{
            Swal.fire({
              icon: 'success',
              title: 'Login Successful',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.authService.setToken(data.data);
              this.router.navigate(['dashboard']);
            });
          }
          
        });
    }
  }
}
