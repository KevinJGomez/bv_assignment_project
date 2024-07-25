import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VerifyUserDirective } from './verify-user.directive';

@NgModule({
  declarations: [VerifyUserDirective],
  imports: [CommonModule],
  exports: [VerifyUserDirective]
})
export class DirectivesModule { }
