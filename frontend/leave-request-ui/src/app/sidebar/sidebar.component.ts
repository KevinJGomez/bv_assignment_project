import { Component,Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  @Input() isToggled: boolean = false;
  @Output() selectOption = new EventEmitter<string>();

  onOptionClick(option: string) {
    this.selectOption.emit(option);
  }
}
