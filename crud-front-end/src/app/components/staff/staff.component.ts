import { CreateStaffFormComponent } from './create-staff-form';
import {
  AfterViewInit,
  Component,
  OnInit,
  ViewChild,
  ɵflushModuleScopingQueueAsMuchAsPossible,
} from '@angular/core';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { AuthService } from '../../auth.service';
import { StaffService } from '../../services/staff.service';
import { Staff } from '../../interfaces/staff';

import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';

import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { ViewStaffComponent } from './view-staff/view-staff.component';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatInputModule } from '@angular/material/input';

import { EditStaffComponent } from './edit-staff/edit-staff.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DeleteDialogComponent } from './delete-dialog/delete-dialog.component';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-staff',
  standalone: true,
  imports: [
    MatTableModule,
    RouterModule,
    MatIconModule,
    MatFormFieldModule,
    MatButtonModule,
    MatTooltipModule,
    MatListModule,
    RouterModule,
    MatPaginatorModule,
    MatInputModule,
    MatPaginator,
    MatSortModule,
    ReactiveFormsModule,
    MatTooltipModule,
    MatDialogModule,
    MatSelectModule,
    // HttpClientModule,
    FormsModule,
  ],
  providers: [AuthService, StaffService],
  templateUrl: './staff.component.html',
  styleUrl: './staff.component.scss',
})
export class StaffComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = [
    'id',
    'fullName',
    'email',
    'terminal',
    'role',
    'status',
    'createdBy',
    'updatedBy',
    'createdAt',
    'updatedAt',
    'deleted',
    'actions',
  ];

  dataSource = new MatTableDataSource<Staff>();

  selectedFilter: string = 'fullName';

  // view child paginator and sort

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private staffService: StaffService,
    public dialog: MatDialog // private router: Router
  ) {}

  ngOnInit(): void {
    this.getStaffList();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  // logics for get all staff
  getStaffList(): void {
    this.staffService.getStaffList().subscribe(
      (data: Staff[]) => {
        this.dataSource.data = data;
        console.log('Staff list fetch successfully');
      },
      (error) => {
        console.error('Error fetching staff list: ', error);
        /* console.error('Error status:', error.status);
        console.error('Error message:', error.message);
        console.error('Error headers:', error.headers);

        if (error.status === 403) {
          console.error('Access forbidden. Check token validity or permission');
        } */
      }
    );
  }

  // logics for edit staff

  editStaff(id: string, updatedStaff: Staff): void {
    this.staffService.updateStaff(id, updatedStaff).subscribe({
      next: (staff: Staff) => {
        this.getStaffList(); // Refresh the list after successful update
      },
      error: (error) => {
        console.error('Error updating staff:', error);
      },
    });
  }

  // dialog for create staff form

  openDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    const dialogRef = this.dialog.open(CreateStaffFormComponent, {
      width: '80%',
      enterAnimationDuration,
      exitAnimationDuration,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getStaffList(); // Refresh the list after successful addition
      }
    });
  }

  // Open dialog to view staff by id
  openView(
    enterAnimationDuration: string,
    exitAnimationDuration: string,
    staffId: string
  ): void {
    this.staffService.getViewStaff(staffId).subscribe({
      next: (staff: Staff) => {
        this.dialog.open(ViewStaffComponent, {
          width: '80%',
          enterAnimationDuration,
          exitAnimationDuration,
          data: staff,
        });
      },
      error: (err) => {
        console.error('Error fetching staff details', err);
      },
    });
  }

  // open dialog edit staff
  openEditDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string,
    staff: Staff
  ): void {
    const dialogRef = this.dialog.open(EditStaffComponent, {
      width: '80%',
      enterAnimationDuration,
      exitAnimationDuration,
      data: staff,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.editStaff(staff.id, result); // Refresh the list after successful addition
      }
    });
  }

  // confirm delete dialog
  openDeleteDialog(staff: Staff): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '320px',
      data: { id: staff.id, fullName: staff.fullName },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getStaffList();
      }
    });
  }

  // filter logic

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value
      .trim()
      .toLowerCase();

    // Update filter function based on selected column
    this.dataSource.filterPredicate = (data: Staff, filter: string) => {
      const columnValue = (data as any)[this.selectedFilter]; // Access column value dynamically
      return columnValue
        ? columnValue.toString().toLowerCase().includes(filter.toLowerCase())
        : false;
    };

    this.dataSource.filter = filterValue;

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
