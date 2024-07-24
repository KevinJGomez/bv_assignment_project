import { Component,Input, EventEmitter, Output,ChangeDetectorRef  } from '@angular/core';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  @Input() isToggled: boolean = false;
  @Output() viewChange = new EventEmitter<string>();

  constructor(private cdr: ChangeDetectorRef) {}

  changeView(view: string) {
    this.viewChange.emit(view);
    this.cdr.detectChanges(); // Force change detection
  }
}
