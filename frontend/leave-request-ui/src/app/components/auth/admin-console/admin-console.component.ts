import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-admin-console',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './admin-console.component.html',
  styleUrl: './admin-console.component.scss'
})
export class AdminConsoleComponent {
  user = {
    username: '',
    password: '',
    role: ''
  };

  users = [
    { id: 1, username: 'john', role: 'ADMIN' },
    { id: 2, username: 'jane', role: 'USER' }
  ];

  onSubmit() {
    const newUser = {
      id: this.users.length + 1,
      ...this.user
    };
    this.users.push(newUser);
    this.user = {
      username: '',
      password: '',
      role: ''
    };
  }
}
