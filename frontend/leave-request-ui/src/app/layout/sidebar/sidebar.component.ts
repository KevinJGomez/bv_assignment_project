import { Component,Input, EventEmitter, Output,ChangeDetectorRef, OnInit  } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule,CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent{
  @Input() isToggled: boolean = false;
  @Output() viewChange = new EventEmitter<string>();
  @Input() username!: string;
  @Input() role!: string;

  constructor(private cdr: ChangeDetectorRef) {}

  changeView(view: string) {
    this.viewChange.emit(view);
    this.cdr.detectChanges(); // Force change detection
  }

  
}
