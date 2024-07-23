import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './signup/signup.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RequestLeaveComponent } from './request-leave/request-leave.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: SignUpComponent },
  { path: 'dashboard', component: DashboardComponent, children: [
    { path: 'request-leave', component: RequestLeaveComponent },
    // { path: 'leave-list', component: LeaveListComponent },
    { path: '', redirectTo: 'request-leave', pathMatch: 'full' }
  ] },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];