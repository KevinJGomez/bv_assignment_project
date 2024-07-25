import { Directive, HostListener } from '@angular/core';
import { AuthServiceService } from '../../services/auth-service.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { catchError, map, first } from 'rxjs/operators';

@Directive({
  selector: '[appVerifyUser]'
})
export class VerifyUserDirective {
    sendData: any = {};
  constructor(private authService: AuthServiceService, private router: Router) {}

  @HostListener('click')
  onClick(): void {
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
              }
            });
  }
}
}