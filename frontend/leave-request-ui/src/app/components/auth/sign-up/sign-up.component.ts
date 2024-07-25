import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, AbstractControl, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthServiceService } from '../../../services/auth-service.service';
import Swal from 'sweetalert2';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { catchError, map, first } from 'rxjs/operators';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {
  signUpForm!: FormGroup;
  sendData: any = {};

  constructor(private fb: FormBuilder, private authService: AuthServiceService, private router: Router) {}

  ngOnInit(): void {
    this.signUpForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;
    if (password !== confirmPassword) {
      return { mismatch: true };
    }
    return null;
  }

  onSubmit() {
    if (this.signUpForm.status === 'INVALID') {
      if(this.signUpForm.get('password')?.invalid){
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
        username: this.signUpForm.controls['username'].value,
        password: this.signUpForm.controls['password'].value,
        role: "USER",
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
          if(data.resCode!=="0"){
            Swal.fire({
              icon: 'error',
              title: 'Sign-up Failed',
              text: data.data,
            });
          }
          else{
            Swal.fire({
              icon: 'success',
              title: 'Signup Successful',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.router.navigate(['/']);
            });
          }
        });
    }
  }
}
