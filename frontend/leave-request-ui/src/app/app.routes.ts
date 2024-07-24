import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MainDashboardComponent } from './main-dashboard/main-dashboard.component';
import { LeaveRequestComponent } from './leave-request/leave-request.component';
import { LeaveRequestListComponent } from './leave-request-list/leave-request-list.component';
export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'dashboard', component: MainDashboardComponent, children: [
        { path: 'request-leave', component: LeaveRequestComponent },
        { path: 'leave-list', component: LeaveRequestListComponent },
        { path: '', redirectTo: 'request-leave', pathMatch: 'full' }
      ] },
      { path: '', redirectTo: '/login', pathMatch: 'full' },
      { path: '**', redirectTo: '/login' }
];
