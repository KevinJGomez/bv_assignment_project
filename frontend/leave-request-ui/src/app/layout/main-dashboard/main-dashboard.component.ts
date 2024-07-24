import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { HeaderComponent } from '../header/header.component';
import { LeaveRequestComponent } from '../../components/leave-request/leave-request.component';
import { LeaveRequestListComponent } from '../../components/leave-request-list/leave-request-list.component';

@Component({
  selector: 'app-main-dashboard',
  standalone: true,
  imports: [SidebarComponent, HeaderComponent, LeaveRequestComponent, LeaveRequestListComponent, RouterOutlet],
  templateUrl: './main-dashboard.component.html',
  styleUrl: './main-dashboard.component.scss'
})
export class MainDashboardComponent {
  isToggled = false;
  showLeaveList = false;

  toggleSidebar() {
    this.isToggled = !this.isToggled;
  }

  showLeaveRequestList() {
    this.showLeaveList = true;
  }

  showLeaveRequestForm() {
    this.showLeaveList = false;
  }

  onSelectOption(option: string) {
    if (option === 'leave-request-list') {
      this.showLeaveRequestList();
    } else {
      this.showLeaveRequestForm();
    }
  }
}
