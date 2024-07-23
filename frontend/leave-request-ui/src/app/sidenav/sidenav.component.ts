import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { MatNavList } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { ToggleSidenavService } from './toggle-sidenav.service'; // import the shared service

@Component({
  selector: 'app-sidenav',
  standalone: true,
  imports: [MatSidenavModule, MatNavList, MatToolbarModule],
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements AfterViewInit {
  @ViewChild(MatSidenav) sidenav!: MatSidenav;

  constructor(private toggleSidenavService: ToggleSidenavService) {}

  ngAfterViewInit() {
    this.toggleSidenavService.toggle$.subscribe(() => {
      this.sidenav.toggle();
    });
  }

  close(): void {
    this.sidenav.close();
  }

  toggle(): void {
    this.sidenav.toggle();
  }
}
