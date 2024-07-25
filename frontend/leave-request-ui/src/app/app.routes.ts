import { Routes } from '@angular/router';
import { LeaveRequestComponent } from './components/leave-request/leave-request.component';
import { MainDashboardComponent } from './layout/main-dashboard/main-dashboard.component';
import { LeaveRequestListComponent } from './components/leave-request-list/leave-request-list.component';
import { LoginComponent } from './components/auth/login/login.component';
import { SignUpComponent } from './components/auth/sign-up/sign-up.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignUpComponent },
    { path: 'dashboard', component: MainDashboardComponent, children: [
        { path: '', component: LeaveRequestComponent },
      ] },
      { path: '', redirectTo: '/login', pathMatch: 'full' },
      { path: '**', redirectTo: '/login' }
];
