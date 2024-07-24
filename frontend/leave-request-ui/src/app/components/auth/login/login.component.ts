import { Component } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  AsyncValidator,
} from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, map, first } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  myForm!: FormGroup;
  sendData: any = {};
}
