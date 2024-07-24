import { Component,ChangeDetectorRef  } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { HeaderComponent } from '../header/header.component';
import { LeaveRequestComponent } from '../../components/leave-request/leave-request.component';
import { LeaveRequestListComponent } from '../../components/leave-request-list/leave-request-list.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-main-dashboard',
  standalone: true,
  imports: [SidebarComponent, HeaderComponent, LeaveRequestComponent, LeaveRequestListComponent, RouterOutlet,CommonModule],
  templateUrl: './main-dashboard.component.html',
  styleUrl: './main-dashboard.component.scss'
})
export class MainDashboardComponent {
  isToggled = false;
  currentView: string = 'leave-request'; // Default view

  constructor(private cdr: ChangeDetectorRef) {}

  toggleSidebar() {
    this.isToggled = !this.isToggled;
    this.cdr.detectChanges(); // Force change detection
  }

  setView(view: string) {
    this.currentView = view;
    this.cdr.detectChanges(); // Force change detection
  }
}
