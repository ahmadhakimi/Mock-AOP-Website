import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatListModule } from '@angular/material/list';
import { StaffService } from '../../../services/staff.service';
import { Staff } from '../../../interfaces/staff';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-view-staff',
  standalone: true,
  imports: [MatListModule, MatCardModule],
  templateUrl: './view-staff.component.html',
  styleUrl: './view-staff.component.scss',
  providers: [
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: { showError: true },
    },
    StaffService,
  ],
})
export class ViewStaffComponent {
  staff: Staff;
  constructor(@Inject(MAT_DIALOG_DATA) public data: Staff) {
    this.staff = data;
  }
}
