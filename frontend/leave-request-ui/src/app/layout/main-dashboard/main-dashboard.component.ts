import { Component,ChangeDetectorRef,OnInit  } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { HeaderComponent } from '../header/header.component';
import { LeaveRequestComponent } from '../../components/leave-request/leave-request.component';
import { LeaveRequestListComponent } from '../../components/leave-request-list/leave-request-list.component';
import { CommonModule } from '@angular/common';
import { AdminConsoleComponent } from '../../components/auth/admin-console/admin-console.component';
import { AuthServiceService } from '../../services/auth-service.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { catchError, map, first } from 'rxjs/operators';
import { DirectivesModule } from './directive.module';
@Component({
  selector: 'app-main-dashboard',
  standalone: true,
  imports: [SidebarComponent, HeaderComponent, LeaveRequestComponent, LeaveRequestListComponent, RouterOutlet,CommonModule,AdminConsoleComponent,DirectivesModule ],
  templateUrl: './main-dashboard.component.html',
  styleUrl: './main-dashboard.component.scss'
})
export class MainDashboardComponent implements OnInit{
  isToggled = false;
  currentView: string = 'leave-request'; // Default view
  sendData: any = {};
  username!:string;
  role!:string;

  constructor(private cdr: ChangeDetectorRef, private authService: AuthServiceService, private router: Router) {}

  toggleSidebar() {
    this.isToggled = !this.isToggled;
    this.cdr.detectChanges(); // Force change detection
  }

  setView(view: string) {
    this.currentView = view;
    this.cdr.detectChanges(); // Force change detection
  }
  
  ngOnInit(): void {
    if(sessionStorage.getItem("__tk") == undefined || sessionStorage.getItem("__tk") == null){
      Swal.fire({
        icon: 'error',
        title: 'Unauthorized Access',
        showConfirmButton: false,
        timer: 800
      }).then(() => {
        this.router.navigate(['/']);
      });
    }
    else{
      this.sendData={
        "token": sessionStorage.getItem("__tk")
      }
      this.authService
          .verify(this.sendData)
          .pipe(
            first(),
            catchError((err) => {
              return err;
            })
          )
          .subscribe((data: any) => {
            if(data.validToken !== "true"){
              Swal.fire({
                icon: 'error',
                title: 'Unauthorized Access',
                showConfirmButton: false,
                timer: 800
              }).then(() => {
                this.router.navigate(['/']);
              });
            }
            else{
              this.username = data.username
              this.role = data.role
            }
          });
    }
  }



}
