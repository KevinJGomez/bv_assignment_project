import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveRequestModalComponent } from './leave-request-modal.component';

describe('LeaveRequestModalComponent', () => {
  let component: LeaveRequestModalComponent;
  let fixture: ComponentFixture<LeaveRequestModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeaveRequestModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeaveRequestModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
