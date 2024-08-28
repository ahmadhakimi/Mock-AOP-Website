import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { CompanyService } from '../../services/company.service';
import { Company } from '../../interfaces/company';
import { response } from 'express';
import { MatCardModule } from '@angular/material/card';
import { MatTable, MatTableModule } from '@angular/material/table';
import { NgIf } from '@angular/common';
import { error } from 'console';
import {
  MatSort,
  matSortAnimations,
  MatSortModule,
} from '@angular/material/sort';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CreateCompanyComponent } from './create-company/create-company.component';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { ViewCompanyComponent } from './view-company/view-company.component';
import { UpdateCompanyComponent } from './update-company/update-company.component';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-company',
  standalone: true,
  imports: [
    MatCardModule,
    MatTableModule,
    NgIf,
    MatSortModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInput,
    MatSelectModule,
    MatPaginator,
    MatSort,
    FormsModule
  ],
  templateUrl: './company.component.html',
  styleUrl: './company.component.scss',
  providers: [CompanyService],
})
export class CompanyComponent implements AfterViewInit {
  // dataSource: any[] = [];
  dataSource: MatTableDataSource<Company>;

  displayColumn: string[] = [
    'id',
    'companyName',
    'rocExpDate',
    'contact',
    'companyEmail',
    'companyStatus',
    'address',
    'createdBy',
    'updatedBy',
    'actions',
  ];

  selectedFilter: string = 'companyName';

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private companyService: CompanyService,
    public dialog: MatDialog
  ) {
    this.dataSource = new MatTableDataSource<Company>([]);
  }

  ngOnInit(): void {
    this.getCompanyList();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  // company list

  getCompanyList(): void {
    this.companyService.getCompanyList().subscribe(
      (data) => {
        console.log('Received data:', data);
        this.dataSource.data = data;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  viewDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string,
    companyId: string
  ) {
    this.companyService.viewCompany(companyId).subscribe({
      next: (company: Company) => {
        this.dialog.open(ViewCompanyComponent, {
          width: '80%',
          enterAnimationDuration,
          exitAnimationDuration,
          data: company,
        });
      },
    });
  }

  updateDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string,
    company: Company
  ) {
    const dialogRef = this.dialog.open(UpdateCompanyComponent, {
      width: '80%',
      enterAnimationDuration,
      exitAnimationDuration,
      data: company,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getCompanyList();
      }
    });
  }

  deleteDialog(company: Company) {}

  // by open dialog to create company
  openDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    const dialogRef = this.dialog.open(CreateCompanyComponent, {
      width: '80%',
      enterAnimationDuration,
      exitAnimationDuration,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getCompanyList(); // Refresh the list after successful addition
      }
    });
  }

  //  filter logic

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value
      .trim()
      .toLowerCase();

    // Update filter function based on selected column
    this.dataSource.filterPredicate = (data: Company, filter: string) => {
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
