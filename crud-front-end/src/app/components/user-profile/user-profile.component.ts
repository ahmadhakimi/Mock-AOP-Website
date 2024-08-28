import { JsonPipe, NgFor, NgIf } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { Staff, Terminal, Status, Role } from '../../interfaces/staff';
import { StaffService } from '../../services/staff.service';
import { error } from 'console';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInput,
    ReactiveFormsModule,
    NgFor,
    NgIf,
    MatButtonModule,
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss',
})
export class UserProfileComponent {
  updateProfileForm: FormGroup;

  terminals = Object.values(Terminal);
  statuses = Object.values(Status);
  roles = Object.values(Role);

  constructor(private fb: FormBuilder, private staffService: StaffService) {
    this.updateProfileForm = this.fb.group({
      staff_id: [''],
      staff_fullname: [''],
      staff_username: '',
      staff_email: '',
      staff_role: '',
      staff_terminal: '',
      staff_status: '',
      staff_created_by: '',
      staff_updated_by: '',
      staff_created_at: '',
      staff_updated_at: '',
    });
  }

  ngOnInit(): void {
    const currentUserJson = sessionStorage.getItem('currentUser');

    console.log('Current User JSON retrieved: ', currentUserJson);

    if (currentUserJson) {
      const currentUser = JSON.parse(currentUserJson);

      console.log('Current user retrieve', currentUser);

      const staffId = currentUser.staffId;

      console.log('Current staff ID retrieve', staffId);

      if (staffId) {
        this.staffService.getViewStaff(staffId).subscribe(
          (user: Staff) => {
            const createdAt = user.createdAt
              ? new Date(user.createdAt)
              : undefined;
            const updatedAt = user.updatedAt
              ? new Date(user.updatedAt)
              : undefined;

            const formattedCreatedAt = createdAt
              ? this.formatDate(createdAt)
              : '';
            const formattedUpdatedAt = updatedAt
              ? this.formatDate(updatedAt)
              : '';

            this.updateProfileForm.patchValue({
              staff_id: user.id,
              staff_fullname: user.fullName,
              staff_username: user.userName,
              staff_email: user.email,
              staff_role: user.role,
              staff_status: user.status,
              staff_terminal: user.terminal,
              staff_created_by: user.createdBy,
              staff_updated_by: user.updatedBy,
              staff_created_at: formattedCreatedAt, // Pass formatted date or empty string
              staff_updated_at: formattedUpdatedAt, // Pass formatted date or empty string
            });
          },
          (error) => {
            console.error('Failed to retrieve user data: ', error);
          }
        );
      } else {
        console.error("Failed to retrieve current user's id: ", staffId);
      }
    } else {
      console.error('Cannot retrieve current user', currentUserJson);
    }
  }

  // Helper function to format date to yyyy-mm-dd
  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = `0${date.getMonth() + 1}`.slice(-2); // Adding leading zero
    const day = `0${date.getDate()}`.slice(-2); // Adding leading zero
    return `${year}-${month}-${day}`;
  }

  onSaveClick() {}
}
