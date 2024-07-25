import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  @Output() toggle = new EventEmitter<void>();

  onToggle() {
    this.toggle.emit();
  }

  constructor(private router: Router){}

  logout(){
    
    Swal.fire({
      title: 'Logout From System',
      text: 'Are you sure you want to logout?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes',
      cancelButtonText: 'No'
    }).then((result) => {
      if (result.isConfirmed) {
        sessionStorage.removeItem('__tk');
        Swal.fire({
          icon: 'success',
          title: 'Logged out successfully!',
          showConfirmButton: false,
          timer: 800
        }).then(() => {
          this.router.navigate(['/']);
        });
      }
    });
  }
}
