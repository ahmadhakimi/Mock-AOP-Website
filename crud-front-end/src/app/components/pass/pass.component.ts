import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { CreatePassFormComponent } from './create-pass-form/create-pass-form.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-pass',
  standalone: true,
  imports: [MatButtonModule, MatCardModule, MatIconModule, MatFormFieldModule],
  templateUrl: './pass.component.html',
  styleUrl: './pass.component.scss',
})
export class PassComponent implements OnInit {
  constructor(private dialog: MatDialog) {}

  ngOnInit(): void {}

  openDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    const dialogRef = this.dialog.open(CreatePassFormComponent, {
      width: '80%',
      enterAnimationDuration,
      exitAnimationDuration,
    });

    // dialogRef.afterClosed().subscribe((result) => {
    //   if (result) {
    //     this.getCompanyList(); // Refresh the list after successful addition
    //   }
    // });
    // 4;
  }
}
